
package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Usuario{
    
    private List<String> historialAcciones = new ArrayList<>();

    public Admin(Builder builder) {
        super(builder);
    }
    
    public void addAccion(String accion) {
        if(this.historialAcciones==null){
            this.historialAcciones = new ArrayList<>();
        }
        this.historialAcciones.add(accion);
    }
    
    public List<String> getHistorialAcciones(){
        
        return this.historialAcciones;
    }
    
    public void addAccion(String accion, String fecha){
        StringBuilder nuevaAccion = new StringBuilder();
        nuevaAccion.append(this.getNombre()+" ");
        nuevaAccion.append(accion);
        nuevaAccion.append("-"+fecha);
        historialAcciones.add(nuevaAccion.toString());
        
    }

    @Override
    public String toString() {
        return "Admin"+super.toString()+"\n{" + "historialAcciones=" + historialAcciones.toString() + '}';
    }
    

    
}
