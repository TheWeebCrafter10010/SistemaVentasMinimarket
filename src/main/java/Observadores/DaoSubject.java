package Observadores;

public interface DaoSubject {
    void attach(DaoObserver observer);
    void detach(DaoObserver observer);
    void notifyObservers(String mensaje);
}
