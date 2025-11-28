package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    private List<Venta> compras = new ArrayList<Venta>();
    private String datosFormato;

    public Cliente(Builder builder){
        super(builder);
    }

    public List<Venta> getCompras() {
        return compras;
    }

    @Override
    public String toString() {
        if(datosFormato == null){
            StringBuilder sb = new StringBuilder();

            sb.append("\n").append("=".repeat(50)).append("\n");
            sb.append("           DATOS DEL CLIENTE\n");
            sb.append("=".repeat(50)).append("\n");

            sb.append(String.format("%-15s : %s%n", "ID", this.getId()));
            sb.append(String.format("%-15s : %s %s%n", "Cliente", this.getNombre(), this.getApellido()));
            sb.append(String.format("%-15s : %s%n", "Email", this.getEmail()));
            sb.append(String.format("%-15s : %s%n", "Teléfono", this.getTelefono()));
            sb.append(String.format("%-15s : %s%n", "Dirección", this.getDireccion()));

            sb.append("=".repeat(50)).append("\n");

            datosFormato = sb.toString();
        }
        return datosFormato;
    }
}