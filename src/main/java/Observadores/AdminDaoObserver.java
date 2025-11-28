package Observadores;

import Entidades.Admin;
import Utils.FechaFormato;

public class AdminDaoObserver implements DaoObserver {

    private Admin admin;

    public AdminDaoObserver(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void notificarCambio(String mensaje) {
        mensaje = FechaFormato.fechaHoraActual()+" - "+mensaje;
        admin.addNotificacion(mensaje);
    }

}
