package Servicios;

import BaseDatos.IProductosDAO;
import BaseDatos.IVentasDAO;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Map;


public class VentasServicio {


    private IVentasDAO ventasDAO;
    private DetalleVentaServicio detalleVentaServicio;
    private IProductosDAO productosDAO;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public VentasServicio(IVentasDAO ventasDAO, DetalleVentaServicio detalleVentaServicio,IProductosDAO productosDAO) {
        this.ventasDAO = ventasDAO;
        this.detalleVentaServicio = detalleVentaServicio;
        this.productosDAO = productosDAO;
    }

    public Producto obtenerProducto(int idProducto){
        return productosDAO.obtenerProductoPorID(idProducto);
    }
    public Map<Integer, Producto> obtenerProductos(){
        return productosDAO.obtenerProductos();
    }

    public boolean agregarProductoAVenta(Producto producto, int cantidad, List<DetalleVenta> detalleVenta){
        DetalleVenta detalle = detalleVentaServicio.crearDetalleVenta(producto, cantidad);

        if(detalle != null) {
            detalleVenta.add(detalle);
            return true;
        }
        return  false;

    }

    public boolean verificarProductoEnLista(Producto producto, List<DetalleVenta> detallesVenta){
        for(DetalleVenta detalle : detallesVenta){
            if(detalle.getProducto().getId() == producto.getId()){
                return true;
            }
        }
        return false;
    }

    public Venta generarVenta(List<DetalleVenta> detallesVenta, int idCliente) {

        LocalDateTime fecha = LocalDateTime.now();
        String fechaFormateada = fecha.format(formatter);

        double totalVenta = 0.0;
        for (DetalleVenta detalle : detallesVenta) {
            totalVenta += detalle.getTotalDetalleVenta();
        }

        Venta nuevaVenta = new Venta(fecha, fechaFormateada, detallesVenta, totalVenta, idCliente);
        ventasDAO.insertarVenta(nuevaVenta);
        actualizarStockProductos(nuevaVenta);
        return ventasDAO.obtenerUltimaVenta();
    }

    private void actualizarStockProductos(Venta venta){

        //Una vez creada la venta e insertada en la base de datos, se actualiza el stock de los productos vendidos
        for(DetalleVenta detalle : venta.getProductosVendidos()){
            Producto producto = detalle.getProducto();
            int nuevoStock = producto.getStock() - detalle.getCantidad();
            producto.setStock(nuevoStock);
        }
    }
}
