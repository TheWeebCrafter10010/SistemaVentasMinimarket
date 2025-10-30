package Entidades;

public class DetalleVenta {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public DetalleVenta(Producto producto, int cantidad) {
        //Constructor cuando se crea un nuevo detalle de venta en el programa
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }
    public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
        //Constructor cuando se lee un detalleVenta de la BD
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public double getTotalDetalleVenta() {
        return cantidad*precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }
    public int getCantidad() {
        return cantidad;
    }
    @Override
    public String toString() {
        return "DetalleVenta {" +
                "Producto = " + producto.getNombre() +
                ", Precio = " + precioUnitario +
                ", Cantidad = " + cantidad +
                ", Total = " + getTotalDetalleVenta() +
                '}';
    }


}
