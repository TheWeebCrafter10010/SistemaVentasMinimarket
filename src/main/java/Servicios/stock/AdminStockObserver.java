package Servicios.stock;

import Entidades.Admin;

public class AdminStockObserver implements StockObserver {

    private Admin admin;

    public AdminStockObserver(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void actualizarStock(String mensaje) {
        // registrar en historial de acciones del admin
        admin.addAccion(mensaje);
        // además imprimir por consola para visibilidad
        System.out.println("[NOTIFICACIÓN a " + admin.getNombre() + "]: " + mensaje);
    }
}
