package main;


import DatosPruebas.*;
import Entidades.Admin;
import Entidades.Usuario;
import DatosPruebas.VentasDAO;
import Servicios.LoginRegistro.ServicioLoginUsuario;
import Servicios.LoginRegistro.ServicioRegistroClienteFacade;
import Servicios.Validacion.ServicioValidacionUsuario;
import Servicios.Ventas.VentasServicio;
import Observadores.AdminDaoObserver;

public class SistemaContexto {


    public static InterfazLogin crearInterfaz() {
    // Crear DAOs
    UsuariosDAO usuarioDAO = new UsuariosDAO();
    ProductosDAO productosDAO = new ProductosDAO();
    VentasDAO ventasDAO = new VentasDAO();

    // Registrar admins como observadores (recorrer usuarios y tomar Admins)
    for (Usuario u : usuarioDAO.obtenerClientes().values()) {
        if (u instanceof Admin) {
            Admin admin = (Admin) u;
            AdminDaoObserver obs = new AdminDaoObserver(admin);
            productosDAO.attach(obs);
            usuarioDAO.attach(obs);
        }
    }

    // Crear Servicios
    var servicioValidacionUsuario = new ServicioValidacionUsuario(usuarioDAO);

    var servicioRegistroCliente = new ServicioRegistroClienteFacade(servicioValidacionUsuario,usuarioDAO);
    var servicioLoginUsuario = new ServicioLoginUsuario(servicioValidacionUsuario, usuarioDAO);
    var ventasServicio = new VentasServicio(ventasDAO, productosDAO);

    InterfazAdmin adminUI = new InterfazAdmin(productosDAO, usuarioDAO, ventasDAO);
    InterfazUsuario usuarioUI = new InterfazUsuario(ventasServicio);


    var interfaz = new InterfazLogin(servicioRegistroCliente, servicioLoginUsuario,
            adminUI, usuarioUI);

    return interfaz;


}
}