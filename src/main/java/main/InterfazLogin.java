package main;

import DataTransferObjects.ValidacionRegistro;
import Entidades.*;
import Servicios.ServicioLoginUsuario;
import Servicios.ServicioRegistroClienteFacade;
import Servicios.ValidarInput;
import Servicios.VentasServicio;
import BaseDatos.IProductosDAO;
import BaseDatos.IUsuarioDAO;
import BaseDatos.IVentasDAO;

import java.util.List;
import java.util.Scanner;

public class InterfazLogin {
    VentasServicio ventasServicio;

    ServicioRegistroClienteFacade servicioRegistroCliente;
    ServicioLoginUsuario servicioLoginUsuario;
    Scanner sc = new Scanner(System.in);

    private InterfazUsuario interfazUsuario;
    private IProductosDAO productosDAO;
private IUsuarioDAO usuarioDAO;
private IVentasDAO ventasDAO;

public InterfazLogin(VentasServicio ventasServicio,
                     ServicioRegistroClienteFacade servicioRegistroCliente,
                     ServicioLoginUsuario servicioLoginUsuario,
                     IProductosDAO productosDAO,
                     IUsuarioDAO usuarioDAO,
                     IVentasDAO ventasDAO) {

    this.servicioRegistroCliente = servicioRegistroCliente;
    this.ventasServicio = ventasServicio;
    this.servicioLoginUsuario = servicioLoginUsuario;
    this.productosDAO = productosDAO;
    this.usuarioDAO = usuarioDAO;
    this.ventasDAO = ventasDAO;
}

    public void iniciar(){
        boolean valido = true;

        do{
            System.out.println("Bienvenido al minimarket! Seleccione una opcion:");
            System.out.println("1. Login");
            System.out.println("2. Registro");
            System.out.println("3. Salir");

            int opcion = ValidarInput.leerEntero();

            switch(opcion){
                case 1:
                    login();
                    break;
                case 2:
                    registro();
                    break;
                case 3:
                    valido = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }

        }while(valido);
    }

    private void login(){
        System.out.println("Ingrese email:");
        String email = sc.nextLine();
        System.out.println("Ingrese contraseña:");
        String pwd = sc.nextLine();

        boolean formatoValido = servicioLoginUsuario.verificarFormatoValido(email,pwd);
        if(formatoValido){
            Usuario user = servicioLoginUsuario.loginUsuario(email,pwd);
            if(user != null){
                System.out.println("Login exitoso");
                if(user instanceof Admin){
    System.out.println("Bienvenido Admin "+user.getNombre()+"!");
    // Abrir interfaz admin
    InterfazAdmin adminUI = new InterfazAdmin(
        productosDAO,
        usuarioDAO,
        ventasDAO
);

    // Mejor: pasar las instancias creadas en SistemaContexto en vez de crear nuevas.
    // Si no tienes getter, modifica SistemaContexto para pasar las mismas instancias.
    adminUI.start();
} else {
                    System.out.println("Bienvenido Cliente "+user.getNombre()+"!");
                    if(interfazUsuario==null){
                        interfazUsuario = new InterfazUsuario(ventasServicio);
                        System.out.println("Creando la interfaz de usuario...");
                    }
                    interfazUsuario.setCliente((Cliente)user);
                    interfazUsuario.start();

                }

            }else{
                System.out.println("Email o contraseña incorrectos.");
            }
        }else{
            System.out.println("Formato de email o contraseña inválido.");
        }
    }

    private void registro(){
        System.out.println("Ingrese nombre:");
        String nombre = sc.nextLine();
        System.out.println("Ingrese apellido:");
        String apellido = sc.nextLine();
        System.out.println("Ingrese email:");
        String email = sc.nextLine();
        System.out.println("Ingrese contraseña:");
        String pwd = sc.nextLine();
        System.out.println("Ingrese teléfono:");
        String telefono = sc.nextLine();

        Cliente nuevoCliente = new Cliente.Builder()
                                    .setNombre(nombre)
                                    .setApellido(apellido)
                                    .setEmail(email)
                                    .setPwd(pwd)
                                    .setTelefono(telefono)
                                    .buildCliente();

        ValidacionRegistro valido = servicioRegistroCliente.validarCampos(nuevoCliente);

        if(valido.esValido()){

            boolean emailUnico = servicioRegistroCliente.verificarEmailUnico(nuevoCliente.getEmail());

            if(emailUnico){
                servicioRegistroCliente.registrarNuevoCliente(nuevoCliente);
                System.out.println("Registro exitoso.");

            }else{
                System.out.println("El email ya está registrado.");
            }


        }else{
            System.out.println("No se pudo registrar el cliente.");
            System.out.println(valido.mostrarErrores());
        }



    }

}