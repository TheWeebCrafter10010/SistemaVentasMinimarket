
package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Usuario{
    
    private List<String> notificaciones= new ArrayList<>();

    public Admin(Builder builder) {
        super(builder);
    }
    
    public void addNotificacion(String notificacion) {

        this.notificaciones.add(notificacion);
    }
    
    public List<String> getNotificaciones(){
        
        return this.notificaciones;
    }

    @Override
    public String toString() {
        return "Admin"+super.toString()+"\n{" + "historialAcciones=" + notificaciones.toString() + '}';
    }
    

    
}
