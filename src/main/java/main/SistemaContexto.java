package main;

import BaseDatos.IProductosDAO;
import BaseDatos.IUsuarioDAO;
import BaseDatos.IVentasDAO;
import DatosPruebas.*;
import Mocks.VentasDAOMock;
import Servicios.*;

public class SistemaContexto {


    public static InterfazLogin crearInterfaz() {
        //Crear DAOs
        IUsuarioDAO usuarioDAO = new UsuariosDAO();
        IProductosDAO productosDAO = new ProductosDAO();
        IVentasDAO ventasDAO = new VentasDAOMock();

        //Crear Servicios
        var servicioValidacionUsuario = new ServicioValidacionUsuario(usuarioDAO);
        var servicioRegistroCliente = new ServicioRegistroClienteFacade(servicioValidacionUsuario);
        var servicioLoginUsuario = new ServicioLoginUsuario(servicioValidacionUsuario, usuarioDAO);
        var detalleVentaServicio = new Servicios.DetalleVentaServicio();
        var ventasServicio = new VentasServicio(ventasDAO, detalleVentaServicio, productosDAO);


        var interfaz = new InterfazLogin(ventasServicio, servicioRegistroCliente, servicioLoginUsuario);
        return interfaz;


    }
}
