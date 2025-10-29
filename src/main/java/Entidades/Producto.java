package Entidades;

public class Producto {
    private int ID;
    private String nombre;
    private double precio;
    private int stock;
    private String proveedor;
    private String imgURL;

    public Producto(int ID, String nombre, double precio, int stock) {
        this.ID = ID;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return ID;
    }

    public double getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getImgURL() {
        return imgURL;
    }

    //SOLO PARA MOCK, DESPUES SE ELIMINARA
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //SOLO PARA MOCK, DESPUES SE ELIMINARA
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "ID=" + ID +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                '}';
    }
}
