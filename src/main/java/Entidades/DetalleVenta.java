package Entidades;

public class DetalleVenta {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double total;


    public DetalleVenta(Producto producto, int cantidad) {
        //Constructor cuando se crea un nuevo detalle de venta en el programa
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        total = this.precioUnitario * cantidad;
    }
    public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
        //Constructor cuando se lee un detalleVenta de la BD
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        total = this.precioUnitario * cantidad;
    }

    public double getTotal() {
        return this.total;
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
                ", Total = " + getTotal() +
                '}';
    }


    /*
    * Producto:
ID: PK
NOMBRE: STRING
STOCK: INT
PRECIO: DOUBLE
PROVEEDOR: FK
Imagen: String (URL)

detalleVenta:
Producto: producto
Cantidad: int
Venta: FK

Venta:
ID: PK
Fecha: Date
detallesVenta: List

    * */
}
