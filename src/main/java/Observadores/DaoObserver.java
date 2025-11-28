package Observadores;

public interface DaoObserver {
    void notificarCambio(String mensaje); // e.g. "Producto X tiene stock 3 - notificado a Admin"
}
