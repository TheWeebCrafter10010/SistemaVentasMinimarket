package BaseDatos;

import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class ClienteSQliteDao implements IClienteDAO{


    private final String clienteLogin="SELECT * FROM Cliente WHERE email=? AND password=?";
    private final String clientePorEmail="SELECT * FROM Cliente WHERE email=?";
    private final String insertarCliente="INSERT INTO Cliente (id_cli,nombre,apellido,email,password,telefono,direccion) VALUES (?,?,?,?,?,?,?)";
    private final String obtenerClientes="SELECT id_cli,nombre,apellido,email FROM Cliente LIMIT ?";

    private Connection conn = ConexionSQlite.getConnection();

    @Override
    public Cliente obtenerClienteLogin(String email, String pwd) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(clienteLogin);

            pstmt.setString(1, email);
            pstmt.setString(2, pwd);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idCliente = rs.getInt("id_cli");
                return new Cliente.Builder()
                        .setId(idCliente)
                        .setNombre(rs.getString("nombre"))
                        .setApellido(rs.getString("apellido"))
                        .setEmail(rs.getString("email"))
                        .setPwd(rs.getString("password"))
                        .setTelefono(rs.getString("telefono"))
                        .setDireccion(rs.getString("direccion"))
                        .setCompras(obtenerComprasCliente(idCliente))
                        .build();
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
        String sql = "INSERT INTO Cliente (nombre, apellido, email, password, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getPwd());
            stmt.setString(5, cliente.getTelefono());
            stmt.setString(6, cliente.getDireccion());

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
                        .build();
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
}