package main;

import BaseDatos.IProductosDAO;
import BaseDatos.IUsuarioDAO;
import BaseDatos.IVentasDAO;
import DatosPruebas.*;
import Entidades.Usuario;
import Mocks.VentasDAOMock;
import Servicios.*;

public class SistemaContexto {


    public static InterfazLogin crearInterfaz() {
    // Crear DAOs
    IUsuarioDAO usuarioDAO = new UsuariosDAO();
    ProductosDAO productosDAO = new ProductosDAO(); // concrete type to attach observers
    IProductosDAO productosDAOInterface = productosDAO;
    IVentasDAO ventasDAO = new VentasDAOMock();

    // Registrar admins como observadores (recorrer usuarios y tomar Admins)
    for (Usuario u : usuarioDAO.obtenerClientes().values()) {
        if (u instanceof Entidades.Admin) {
            Entidades.Admin admin = (Entidades.Admin) u;
            Servicios.stock.AdminStockObserver obs = new Servicios.stock.AdminStockObserver(admin);
            productosDAO.attach(obs);
        }
    }

    // Crear Servicios
    var servicioValidacionUsuario = new ServicioValidacionUsuario(usuarioDAO);
    var servicioRegistroCliente = new ServicioRegistroClienteFacade(servicioValidacionUsuario);
    var servicioLoginUsuario = new ServicioLoginUsuario(servicioValidacionUsuario, usuarioDAO);
    var detalleVentaServicio = new Servicios.DetalleVentaServicio();
    var ventasServicio = new VentasServicio(ventasDAO, detalleVentaServicio, productosDAOInterface);

    var interfaz = new InterfazLogin(ventasServicio, servicioRegistroCliente, servicioLoginUsuario,
                                    productosDAOInterface, usuarioDAO, ventasDAO);
    return interfaz;


}
}