package BaseDatos;

import Entidades.Producto;
import Entidades.Venta;

import java.util.Date;
import java.util.List;

public interface IVentasDAO {

    Venta obtenerVentaPorID(int id);

    void insertarVenta(Venta venta);//Insertar nueva venta
    List<Venta> obtenerVentas();
    Venta obtenerUltimaVenta();

}
