package BaseDatos;

import Entidades.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class UsuarioSQliteDAO implements IUsuarioDAO {

    private Connection conn = ConexionSQlite.getConnection();

    @Override
    public Usuario obtenerUsuarioLogin(String email, String pwd) {
        String usuarioLogin ="SELECT * FROM Usuario WHERE email=? AND password=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(usuarioLogin);

            pstmt.setString(1, email);
            pstmt.setString(2, pwd);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("id_cli");
                String rol = rs.getString("rol");
                Usuario.Builder builder  = new Usuario.Builder()
                        .setId(idUser)
                        .setNombre(rs.getString("nombre"))
                        .setApellido(rs.getString("apellido"))
                        .setEmail(rs.getString("email"))
                        .setPwd(rs.getString("password"))
                        .setTelefono(rs.getString("telefono"))
                        .setDireccion(rs.getString("direccion"))
                        ;
                if(rol.equals(RolesDB.CLIENTE.getRol())){
                    Cliente cliente = builder.buildCliente();
                    cliente.setCompras(obtenerComprasCliente(idUser));
                    return cliente;
                }
                else if(rol.equals(RolesDB.ADMIN.getRol())){
                    Admin admin = builder.buildAdmin();
                    admin.setHistorialAcciones(obtenerHistorialAccionesAdmin(idUser));
                    return admin;
                }
                
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Error al obtener cliente por login: " + e.getMessage());
        }

        return null;
    }
    
    @Override
    public boolean emailYaRegistrado(String email) {
        String clientePorEmail="SELECT * FROM Usuario WHERE email=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(clientePorEmail);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return  (rs.next());

        } catch (SQLException e) {
            System.out.println("Error al obtener cliente por login: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Usuario (nombre, apellido, email, password, telefono, direccion,rol) VALUES (?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getPwd());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getDireccion());
            stmt.setString(7, RolesDB.CLIENTE.getRol());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
}
    @Override
    public List<Cliente> obtenerClientes(int limite) {
        //EL ADMIN PUEDE USAR ESTE METODO PARA VISUALIZAR LOS CLIENTES REGISTRADOS EN LA PLATAFORMA
        //SOLO SE MUESTRAN ID,NOMBRE,APELLIDO Y EMAIL
        //SI SE QUIERE VER MAS DETALLES DE UN CLIENTE, SE DEBE USAR OTRO METODO (ya depende del controlador :v)

        String obtenerClientes="SELECT id_cli,nombre,apellido,email FROM Usuario LIMIT ? WHERE rol='USER'";

        List<Cliente> clientes = new ArrayList<>(limite);//XDDDDDDDDDD

        try{
            PreparedStatement pstmt = conn.prepareStatement(obtenerClientes);
            pstmt.setInt(1, limite);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int idCliente = rs.getInt("id_cli");
                Cliente cliente = new Cliente.Builder()
                        .setId(idCliente)
                        .setNombre(rs.getString("nombre"))
                        .setApellido(rs.getString("apellido"))
                        .setEmail(rs.getString("email"))
                        .buildCliente();
                clientes.add(cliente);
            }
            rs.close();
            pstmt.close();
            return clientes;

        }catch(Exception e){
            System.out.println("Error al obtener clientes: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insertarAccionAdmin(int idAdmin,String accion,String fecha) {
        String insercionAdmin ="INSERT INTO HistorialAdmin (id_admin,Accion,Fecha) VALUES (?, ?,?)";
        try  {
            PreparedStatement stmt = conn.prepareStatement(insercionAdmin);
            stmt.setInt(1, idAdmin);
            stmt.setString(2, accion);
            stmt.setString(3, fecha);

            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar accion admin: " + e.getMessage());
            return false;
        }
    }

    private List<Venta> obtenerComprasCliente(int idCliente){
        String consulta="SELECT v.id_venta,v.fecha_venta,v.total_venta,d.id_prod,p.nombre,\n" +
                "\t\td.cantidad, d.precio_unitario,p.imgURL\n" +
                "FROM Venta v\n" +
                "INNER JOIN DetalleVenta d ON v.id_venta = d.id_venta\n" +
                "INNER JOIN Producto p ON d.id_prod = p.id_prod\n" +
                "\n" +
                "where v.id_cli = ?\t\t";
        List<Venta> compras = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try{
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.setInt(1, idCliente);
            ResultSet rs = pstmt.executeQuery();

            int idAnterior = 0;
            Venta venta = null;
            while(rs.next()){
                int idVenta = rs.getInt("id_venta");

                if(idVenta!=idAnterior){
                    if(venta!=null){
                        compras.add(venta);
                    }
                    venta = new Venta.Builder()
                            .setId(idVenta)
                            .setFecha(LocalDateTime.parse(rs.getString("fecha_venta"),formatter))
                            .setFechaConFormato(rs.getString("fecha_venta"))
                            .setTotalVenta(rs.getDouble("total_venta"))
                            .setProductosVendidos(new ArrayList<DetalleVenta>())
                            .setIdCliente(idCliente)
                            .build();
                    idAnterior = idVenta;
                }

                Producto producto = new Producto.Builder()
                        .setID(rs.getInt("id_prod"))
                        .setNombre(rs.getString("nombre"))
                        .setImgURL(rs.getString("imgURL"))
                        .build();

                DetalleVenta detalle = new DetalleVenta(producto,rs.getInt("cantidad"),rs.getDouble("precio_unitario"));
                venta.getProductosVendidos().add(detalle);
            }
            rs.close();
            pstmt.close();
            compras.add(venta);
            return compras;
        }catch(SQLException e){
            System.out.println("Error al obtener compras del cliente: " + e.getMessage());
        }
        return null;
    }

    private List<String> obtenerHistorialAccionesAdmin(int idAdmin){
        //Metodo para obtener el historial de acciones de un admin
        String consulta="SELECT Accion,Fecha FROM HistorialAdmin WHERE id_admin=?";
        List<String> historial = new ArrayList<>();
        try{
            PreparedStatement pstmt = conn.prepareStatement(consulta);
            pstmt.setInt(1, idAdmin);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                String accion = rs.getString("Accion");
                String fecha = rs.getString("Fecha");
                historial.add(accion + " - " + fecha);
            }
            rs.close();
            pstmt.close();
            return historial;
        }catch(SQLException e){
            System.out.println("Error al obtener historial de acciones del admin: " + e.getMessage());
        }
        return null;
    }
}