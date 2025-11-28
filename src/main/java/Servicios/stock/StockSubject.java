package Servicios.stock;

public interface StockSubject {
    void attach(StockObserver observer);
    void detach(StockObserver observer);
    void notifyObservers(String mensaje);
}
