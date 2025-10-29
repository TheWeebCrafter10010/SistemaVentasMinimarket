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

    public Venta(LocalDateTime fecha,String fechaConFormato, List<DetalleVenta> productosVendidos) {
        //Constructor para crear Venta y colocarla en BD
        //ID se asigna automaticamente en la BD
        this.fecha = fecha;
        this.productosVendidos = productosVendidos;
        this.fechaConFormato = fechaConFormato;
        calcularTotal();
    }

    public Venta(int id, LocalDateTime fecha, String fechaConFormato, List<DetalleVenta> productosVendidos, double totalVenta) {
        //Constructor para crear Venta al leerla de la BD
        this.id = id;
        this.fecha = fecha;
        this.fechaConFormato = fechaConFormato;
        this.productosVendidos = productosVendidos;
        this.totalVenta = totalVenta;
    }

    private void calcularTotal() {
        double total = 0;
        for (DetalleVenta detalle : productosVendidos) {
            total += detalle.getTotal();
        }
        this.totalVenta = total;
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
}
