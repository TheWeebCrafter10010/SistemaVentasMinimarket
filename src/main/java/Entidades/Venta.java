package Entidades;

import java.util.Date;
import java.util.List;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private LocalDateTime fecha;
    private String fechaConFormato;
    private List<DetalleVenta> productosVendidos;
    private double totalVenta;
    private int idCliente;

    public Venta(Builder builder) {
        this.id = builder.id;
        this.fecha = builder.fecha;
        this.fechaConFormato = builder.fechaConFormato;
        this.productosVendidos = builder.productosVendidos;
        this.totalVenta = builder.totalVenta;
        this.idCliente = builder.idCliente;
    }

    public Venta(LocalDateTime fecha,String fechaConFormato, List<DetalleVenta> productosVendidos, double totalVenta, int idCliente) {
        //Constructor para crear Venta y colocarla en BD
        //ID se asigna automaticamente en la BD
        this.fecha = fecha;
        this.productosVendidos = productosVendidos;
        this.fechaConFormato = fechaConFormato;
        this.idCliente = idCliente;
        this.totalVenta = totalVenta;
    }

    public Venta(int id, LocalDateTime fecha, String fechaConFormato, List<DetalleVenta> productosVendidos, double totalVenta) {
        //Constructor para crear Venta al leerla de la BD
        this.id = id;
        this.fecha = fecha;
        this.fechaConFormato = fechaConFormato;
        this.productosVendidos = productosVendidos;
        this.totalVenta = totalVenta;
    }


    public double getTotal() {
        return this.totalVenta;
    }

    public int getID() {
        return id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public String getFechaConFormato() {
    return fechaConFormato;
}


    public List<DetalleVenta> getProductosVendidos() {
        return productosVendidos;
    }


    public void setId(int id) {//SOLO PARA PRUEBAS
        this.id = id;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venta {")
                .append("ID = ").append(id)
                .append(", Fecha = ").append(fechaConFormato)
                .append(", Total = ").append(getTotal())
                .append(", ProductosVendidos = [\n");

        for (DetalleVenta detalle : productosVendidos) {
            sb.append("   ").append(detalle.toString()).append("\n");
        }

        sb.append("]}");
        return sb.toString();
    }

    public static class Builder {
        private int id;
        private LocalDateTime fecha;
        private String fechaConFormato;
        private List<DetalleVenta> productosVendidos;
        private double totalVenta;
        private int idCliente;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setFecha(LocalDateTime fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder setFechaConFormato(String fechaConFormato) {
            this.fechaConFormato = fechaConFormato;
            return this;
        }

        public Builder setProductosVendidos(List<DetalleVenta> productosVendidos) {
            this.productosVendidos = productosVendidos;
            return this;
        }

        public Builder setTotalVenta(double totalVenta) {
            this.totalVenta = totalVenta;
            return this;
        }

        public Builder setIdCliente(int idCliente) {
            this.idCliente = idCliente;
            return this;
        }

        public Venta build() {
            return new Venta(this);
        }
    }
}
