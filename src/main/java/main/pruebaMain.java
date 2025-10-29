package main;

import BaseDatos.*;
import Entidades.Venta;
import Mocks.*;
import Servicios.*;

public class pruebaMain {
    public static void main(String[] args) {

        IProductosDAO productosDAO = new ProductosDAOMock();
        IVentasDAO ventasDAO = new VentasDAOMock();
        VentasServicio ventasServicio = new VentasServicio(ventasDAO, new DetalleVentaServicio(), productosDAO);
        InterfazConsolaPrueba interfaz = new InterfazConsolaPrueba(productosDAO, ventasServicio);
        interfaz.generarVenta();
    }
}
