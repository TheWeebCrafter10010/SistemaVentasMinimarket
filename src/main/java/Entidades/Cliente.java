package Entidades;

import java.util.List;

public class Cliente extends Usuario{
    private List<Venta> compras;

    public Cliente(Builder builder){
        super(builder);
    }

    public List<Venta> getCompras() {
        return compras;
    }
    public void setCompras(List<Venta> compras){
        this.compras = compras;
    }

    @Override
    public String toString() {
        return "Cliente" + super.toString() +"\n compras="+compras.toString();
    }
}