package BaseDatos;

import Entidades.Producto;

import java.util.List;
import java.util.Map;

public interface IProductosDAO {

    Producto obtenerProductoPorID(int id);

    Map<Integer,Producto> obtenerProductos(); // Búsqueda parcial por nombre

    //Insertar nuevo producto, dvuelve el ID generado
    int insertarProducto(Producto producto);

    void actualizarProducto(Producto producto);
}
