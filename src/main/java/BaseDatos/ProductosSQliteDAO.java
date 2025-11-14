package BaseDatos;

import Entidades.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosSQliteDAO implements IProductosDAO{

    private Connection conn = ConexionSQlite.getConnection();

    @Override
    public Producto obtenerProductoPorID(int id) {
        String consulta = "SELECT * FROM Producto WHERE id_producto = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            if(rs.next()){
                Producto producto = new Producto.Builder()
                        .setID(rs.getInt("id_prod"))
                        .setNombre(rs.getString("nombre"))
                        .setPrecio(rs.getDouble("precio"))
                        .setStock(rs.getInt("stock"))
                        .setImgURL(rs.getString("imgURL"))
                        .build();
                rs.close();
                ps.close();
                return producto;
            }
        }catch(SQLException e){
            System.out.println("Error al obtener producto por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> obtenerProductosPorNombre(String cadenaNombre) {
//        if(cadenaNombre.isEmpty()){
//            return null;
//        }
        String consulta = "SELECT * FROM Producto WHERE nombre LIKE ?";

        try{
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, "%"+cadenaNombre+"%");
            var rs = ps.executeQuery();
            List<Producto> productos;
            if(!rs.next()){
                productos = null;
                return productos;
            }
            productos = new ArrayList<>();
            do{
                Producto producto = new Producto.Builder()
                        .setID(rs.getInt("id_prod"))
                        .setNombre(rs.getString("nombre"))
                        .setPrecio(rs.getDouble("precio"))
                        .setStock(rs.getInt("stock"))
                        .setImgURL(rs.getString("imgURL"))
                        .build();
                productos.add(producto);
            }while(rs.next());
            rs.close();
            ps.close();
            return productos;
        }catch (SQLException e){
            System.out.println("Error al obtener productos por nombre: " + e.getMessage());
        }

        return null;
    }

    @Override
    public int insertarProducto(Producto producto) {
        String consulta = "INSERT INTO Producto (nombre, stock, precio, imgURL) VALUES (?, ?, ?, ?)";

        try{
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getStock());
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getImgURL());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    ps.close();
                    return idGenerado;
                }
            }
            ps.close();
        }catch (SQLException e){
            System.out.println("Error al insertar producto: " + e.getMessage());
        }

        return -1;// Indica que no se pudo insertar
    }

    @Override
    public void actualizarProducto(Producto producto) {
        String consulta = "UPDATE Producto SET nombre = ?, stock = ?, precio = ?, imgURL = ? WHERE id_prod = ?";

        try{
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getStock());
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getImgURL());
            ps.setInt(5, producto.getId());

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    @Override
    public boolean actualizarColumnaProducto(int idProducto, ColumnaProducto columna, Object nuevoValor) {
        columna.getTipo();
        if(!columna.getTipo().isInstance(nuevoValor)){
            return false;
        }
        String consulta = "UPDATE Producto SET " + columna.getColumna() + " = ? WHERE id_prod = ?";

        try{
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setObject(1, nuevoValor);
            ps.setInt(2, idProducto);

            ps.executeUpdate();
            ps.close();
            return true;
        }catch (SQLException e) {
            System.out.println("Error al actualizar columna del producto: " + e.getMessage());
        }

        return false;
    }
}
