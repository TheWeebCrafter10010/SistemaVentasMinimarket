package BaseDatos;

import Entidades.Producto;
import Entidades.Venta;

import java.util.Date;
import java.util.List;

public interface IVentasDAO {

    Venta obtenerVentaPorID(int id);
    List<Venta> obtenerVentasPorFecha(Date fecha); // Búsqueda por fecha (formato "YYYY-MM-DD")
    void insertarVenta(Venta venta);//Insertar nueva venta

    Venta obtenerUltimaVenta();

}
