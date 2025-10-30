package BaseDatos;

import Entidades.Producto;

import java.util.List;

public interface IProductosDAO {

    Producto obtenerProductoPorID(int id);

    List<Producto> obtenerProductosPorNombre(String cadenaNombre); // Búsqueda parcial por nombre

    //Insertar nuevo producto, dvuelve el ID generado
    int insertarProducto(Producto producto);

    void actualizarProducto(Producto producto);

    //Actualizar columna específica dado el ID del producto
    //Devuelve false si el tipo de dato no coincide con el de la columna
    boolean actualizarColumnaProducto(int idProducto, ColumnaProducto columna, Object nuevoValor);

}
