package main;

import BaseDatos.IProductosDAO;
import DataTransferObjects.ValidacionRegistro;
import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;;
import Servicios.ServicioLoginCliente;
import Servicios.ServicioRegistroClienteFacade;
import Servicios.VentasServicio;

import java.util.List;
import java.util.Scanner;

public class InterfazConsolaPrueba {
    VentasServicio ventasServicio;
    IProductosDAO productosDAO;
    ServicioRegistroClienteFacade servicioRegistroCliente;
    ServicioLoginCliente servicioLoginCliente;
    Scanner sc = new Scanner(System.in);

    public InterfazConsolaPrueba(IProductosDAO productosDAO, VentasServicio ventasServicio, ServicioRegistroClienteFacade servicioRegistroCliente,ServicioLoginCliente servicioLoginCliente) {
        this.servicioRegistroCliente = servicioRegistroCliente;
        this.productosDAO = productosDAO;
        this.ventasServicio = ventasServicio;
        this.servicioLoginCliente = servicioLoginCliente;
    }

    public void generarVenta(){
        List<Entidades.DetalleVenta> detallesVenta = new java.util.ArrayList<>();

        boolean continuar = true;

        do{
            System.out.println("Ingresa el ID del producto:");
            int idProducto = sc.nextInt();
            Producto producto = productosDAO.obtenerProductoPorID(idProducto);

            if(producto != null){
                System.out.println(producto);

                if(ventasServicio.verificarProductoEnLista(producto,detallesVenta)){
                    System.out.println("El producto ya fue agregado a la venta.");
                }else {

                    boolean cantidadValida = false;
                    while(!cantidadValida){
                        System.out.println("Ingresa la cantidad a vender:");
                        int cantidad = sc.nextInt();

                        cantidadValida = ventasServicio.agregarProductoAVenta(producto,cantidad,detallesVenta);
                    }

                    System.out.println("¿Deseas agregar otro producto? (s/n)");
                    String respuesta = sc.next();
                    if(respuesta.equalsIgnoreCase("n")){
                        continuar = false;
                    }
                }

            }else{
                System.out.println("Producto no encontrado.");
            }
        }while(continuar);

        Venta nuevaVenta = ventasServicio.generarVenta(detallesVenta,3);//ID cliente hardcodeado para pruebas
        System.out.println(nuevaVenta);
        for(DetalleVenta dv: nuevaVenta.getProductosVendidos()){
            System.out.println(dv.getProducto());
        }

    }

    public void loginPrueba(){
        System.out.println("Ingrese email:");
        String email = sc.nextLine();
        System.out.println("Ingrese contraseña:");
        String pwd = sc.nextLine();

        boolean formatoValido = servicioLoginCliente.verificarFormatoValido(email,pwd);
        if(formatoValido){
            Entidades.Cliente cliente = servicioLoginCliente.loginCliente(email,pwd);
            if(cliente != null){
                System.out.println("Login exitoso. Bienvenido!");
                System.out.println(cliente);
            }else{
                System.out.println("Email o contraseña incorrectos.");
            }
        }else{
            System.out.println("Formato de email o contraseña inválido.");
        }
    }

    public void registroPrueba(){
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

        Cliente nuevoCliente = new Cliente(nombre,apellido,email,pwd,telefono);

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
