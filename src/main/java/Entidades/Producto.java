package Entidades;

public class Producto {
    private int ID;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(Builder builder) {
        this.ID = builder.ID;
        this.nombre = builder.nombre;
        this.precio = builder.precio;
        this.stock = builder.stock;
    }

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

    public static class Builder {
        private int ID;
        private String nombre;
        private double precio;
        private int stock;

        public Builder setID(int ID) {
            this.ID = ID;
            return this;
        }

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setPrecio(double precio) {
            this.precio = precio;
            return this;
        }

        public Builder setStock(int stock) {
            this.stock = stock;
            return this;
        }

        public Producto build() {
            return new Producto(this);
        }
    }
}
