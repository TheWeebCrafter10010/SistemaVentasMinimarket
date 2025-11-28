package DatosPruebas;

import BaseDatos.IVentasDAO;
import Entidades.Venta;

import java.util.ArrayList;
import java.util.List;

public class VentasDAO implements IVentasDAO {

    private List<Venta> ventas = new ArrayList<>();
    private int nextID = 1;

    public VentasDAO() {

    }
    @Override
    public Venta obtenerVentaPorID(int id) {
        for (Venta v : ventas) {
            if (v.getID() == id) return v;
        }
        return null;
    }

    @Override
    public void insertarVenta(Venta venta) {
        venta.setId(nextID);
        ventas.add(venta);
        nextID++;
    }

    @Override
    public Venta obtenerUltimaVenta() {
        if (ventas.isEmpty()) {
            return null;
        }
        return ventas.get(ventas.size() - 1);
    }

    @Override
    public List<Venta> obtenerVentas() {
        return ventas;
    }

}

