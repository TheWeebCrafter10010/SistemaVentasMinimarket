
package Entidades;

import java.util.List;

public class Admin extends Usuario{
    
    private List<String> historialAcciones;

    public Admin(Builder builder) {
        super(builder);
    }
    
    public void setHistorialAcciones(List <String>historial){
        this.historialAcciones = historial;
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
