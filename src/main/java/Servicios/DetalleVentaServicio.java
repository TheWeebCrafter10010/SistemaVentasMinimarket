package Servicios;

import BaseDatos.IProductosDAO;
import Entidades.DetalleVenta;
import Entidades.Producto;

import java.util.List;

public class DetalleVentaServicio {

    public DetalleVenta crearDetalleVenta(Producto producto, int cantidad) {
        DetalleVenta nuevoDetalleVenta=null;

        if(producto.getStock() >= cantidad) {
            nuevoDetalleVenta = new DetalleVenta(producto, cantidad);
        }
        return nuevoDetalleVenta;
        //Si es null no hay stock suficiente
    }
}
