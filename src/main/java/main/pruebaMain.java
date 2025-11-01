package main;

import BaseDatos.*;
import Entidades.Cliente;
import Entidades.Venta;
import Mocks.*;
import Servicios.*;

public class pruebaMain {
    public static void main(String[] args) {

//        IClienteDAO clienteDAO = new ClienteDAOMock();
//        ServicioValidacionCliente servicioValidacionCliente = new ServicioValidacionCliente(clienteDAO);
//        ServicioRegistroClienteFacade servicioRegistroCliente = new ServicioRegistroClienteFacade(servicioValidacionCliente);
//        ServicioLoginCliente servicioLoginCliente = new ServicioLoginCliente(servicioValidacionCliente, clienteDAO);
//
//        IProductosDAO productosDAO = new ProductosDAOMock();
//        IVentasDAO ventasDAO = new VentasDAOMock();
//        VentasServicio ventasServicio = new VentasServicio(ventasDAO, new DetalleVentaServicio(), productosDAO);
//        InterfazConsolaPrueba interfaz =
//                new InterfazConsolaPrueba(productosDAO, ventasServicio,servicioRegistroCliente, servicioLoginCliente);
//
//        interfaz.registroPrueba();
//        interfaz.loginPrueba();

        ClienteSQliteDao clienteSQliteDao = new ClienteSQliteDao();

        Cliente cliente = clienteSQliteDao.obtenerClienteLogin("alonso123@gmail.com","holamundo");
        System.out.println(cliente);
        for(Venta venta : cliente.getCompras()){
            System.out.println(venta);
        }

    }
}
