package Mocks;

import BaseDatos.IVentasDAO;
import Entidades.Venta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class VentasDAOMock implements IVentasDAO {

    private List<Venta> ventas = new ArrayList<>();
    private int nextID = 1;

    public VentasDAOMock() {
        // Ventas de ejemplo

    }

    @Override
    public Venta obtenerVentaPorID(int id) {
        for (Venta v : ventas) {
            if (v.getID() == id) return v;
        }
        return null;
    }

    @Override
    public List<Venta> obtenerVentasPorFecha(Date fecha) {
        /*return ventas.stream()
                .filter(v -> esMismaFecha(v.getFecha(), fecha))
                .collect(Collectors.toList());
        */
        System.out.println("por implementar xd");
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

    // Método auxiliar para comparar solo la parte de fecha (sin horas)
    private boolean esMismaFecha(Date d1, Date d2) {
        return d1.getYear() == d2.getYear()
                && d1.getMonth() == d2.getMonth()
                && d1.getDate() == d2.getDate();
    }
}

