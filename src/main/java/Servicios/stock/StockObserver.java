package Servicios.stock;

public interface StockObserver {
    void actualizarStock(String mensaje); // e.g. "Producto X tiene stock 3 - notificado a Admin"
}
