package main;

import Servicios.Validacion.ValidacionRegistro;
import Entidades.*;
import Servicios.LoginRegistro.ServicioLoginUsuario;
import Servicios.LoginRegistro.ServicioRegistroClienteFacade;
import Utils.ValidarInput;

import java.util.Scanner;

public class InterfazLogin {

    private ServicioRegistroClienteFacade servicioRegistroCliente;
    private ServicioLoginUsuario servicioLoginUsuario;
    private Scanner sc = new Scanner(System.in);

    private InterfazUsuario userUI;
    private InterfazAdmin adminUI;

    public InterfazLogin(ServicioRegistroClienteFacade servicioRegistroCliente,
                         ServicioLoginUsuario servicioLoginUsuario,InterfazAdmin adminUI,
                         InterfazUsuario userUI){

        this.servicioRegistroCliente = servicioRegistroCliente;
        this.servicioLoginUsuario = servicioLoginUsuario;
        this.adminUI = adminUI;
        this.userUI = userUI;
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
                    Admin admin = (Admin)user;
                    System.out.println("Bienvenido Admin "+user.getNombre()+"!");
                    // Abrir interfaz admin

                    adminUI.setAdmin(admin);
                    adminUI.start();
                } else {
                    System.out.println("Bienvenido Cliente "+user.getNombre()+"!");

                    userUI.setCliente((Cliente)user);
                    userUI.start();

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

        ValidacionRegistro valido = servicioRegistroCliente.registrarCliente(nuevoCliente);

        if(valido.esValido()){
            System.out.println("Registro exitoso! Ya puede iniciar sesión.");
        }else{
            System.out.println("Error al registrarse:");
            System.out.println(valido.mostrarErrores());
        }


    }

}