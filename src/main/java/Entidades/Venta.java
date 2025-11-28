package Entidades;

import java.util.Date;
import java.util.List;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private String fechaConFormato;
    private List<DetalleVenta> productosVendidos;
    private double totalVenta;
    private Cliente cliente;
    private String ticket;

    public Venta(String fechaConFormato, List<DetalleVenta> productosVendidos, double totalVenta, Cliente cliente) {

        this.productosVendidos = productosVendidos;
        this.fechaConFormato = fechaConFormato;
        this.cliente = cliente;
        this.totalVenta = totalVenta;
    }

    public double getTotal() {
        return this.totalVenta;
    }

    public int getID() {
        return id;
    }

    public String getFechaConFormato() {
    return fechaConFormato;
}

    public List<DetalleVenta> getProductosVendidos() {
        return productosVendidos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void setId(int id) {//SOLO LO PUEDE USAR EL DAO
        this.id = id;
    }
    @Override
    public String toString() {
        return ticket;
    }

}
