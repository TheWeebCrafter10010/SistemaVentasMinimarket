package Servicios.Ventas;

import BaseDatos.IProductosDAO;
import BaseDatos.IVentasDAO;
import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;
import Utils.FechaFormato;
import java.util.List;

import java.util.Map;


public class VentasServicio {


    private IVentasDAO ventasDAO;
    private IProductosDAO productosDAO;

    public VentasServicio(IVentasDAO ventasDAO,IProductosDAO productosDAO) {
        this.ventasDAO = ventasDAO;
        this.productosDAO = productosDAO;
    }

    public Producto obtenerProducto(int idProducto){
        return productosDAO.obtenerProductoPorID(idProducto);
    }

    public Map<Integer,Producto> obtenerProductos(){
        return productosDAO.obtenerProductos();
    }

    public boolean agregarProductoAVenta(Producto producto, int cantidad, List<DetalleVenta> detalleVenta){
        DetalleVenta nuevoDetalleVenta=null;

        if(producto.getStock() >= cantidad) {
            nuevoDetalleVenta = new DetalleVenta(producto, cantidad);
            detalleVenta.add(nuevoDetalleVenta);
            return true;
        }
        return false;


    }

    public boolean verificarProductoEnLista(Producto producto, List<DetalleVenta> detallesVenta){
        for(DetalleVenta detalle : detallesVenta){
            if(detalle.getProducto().getId() == producto.getId()){
                return true;
            }
        }
        return false;
    }

    public Venta generarVenta(List<DetalleVenta> detallesVenta, Cliente cliente) {

        String fechaFormateada = FechaFormato.fechaHoraActual();

        double totalVenta = 0.0;
        for (DetalleVenta detalle : detallesVenta) {
            totalVenta += detalle.getTotalDetalleVenta();
        }

        Venta nuevaVenta = new Venta(fechaFormateada, detallesVenta, totalVenta, cliente);
        nuevaVenta.setTicket(buildTicket(nuevaVenta));
        ventasDAO.insertarVenta(nuevaVenta);
        cliente.getCompras().add(nuevaVenta);
        actualizarStockProductos(nuevaVenta);
        return ventasDAO.obtenerUltimaVenta();
    }

    private void actualizarStockProductos(Venta venta){

        //Una vez creada la venta e insertada en la base de datos, se actualiza el stock de los productos vendidos
        for(DetalleVenta detalle : venta.getProductosVendidos()){
            Producto producto = detalle.getProducto();
            int nuevoStock = producto.getStock() - detalle.getCantidad();
            producto.setStock(nuevoStock);
            productosDAO.actualizarProducto(producto);
        }
    }

    private String buildTicket(Venta venta){
        //Metodo para construir el ticket de la venta
        //La venta se imprime con su metodo toString que retorna el ticket generado aqui
        StringBuilder sb = new StringBuilder();

        sb.append("\n===================== BOLETA =====================\n");
        sb.append("Cliente: ")
                .append(venta.getCliente().getNombre()).append(" ").append(venta.getCliente().getApellido()).append("\n");
        sb.append("Fecha: ").append(venta.getFechaConFormato()).append("\n");
        sb.append("--------------------------------------------------\n");

        // Encabezado de tabla
        sb.append(String.format("%-20s %-8s %-8s%n", "PRODUCTO", "CANT", "SUBTOTAL"));

        // Filas de productos
        for (DetalleVenta dv : venta.getProductosVendidos()) {
            sb.append(String.format(
                    "%-20s %-8d S/. %-7.2f%n",
                    dv.getProducto().getNombre(),
                    dv.getCantidad(),
                    dv.getTotalDetalleVenta()
            ));
        }

        sb.append("--------------------------------------------------\n");
        sb.append("TOTAL A PAGAR: S/. ").append(venta.getTotal()).append("\n");
        sb.append("==================================================\n");

        return sb.toString();
    }
}
