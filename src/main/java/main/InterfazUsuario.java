package main;

import BaseDatos.IProductosDAO;
import DatosPruebas.ProductosDAO;
import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;
import Servicios.ValidarInput;
import Servicios.VentasServicio;

import java.util.List;

public class InterfazUsuario {
    private VentasServicio ventasServicio;
    private Cliente cliente;
    public InterfazUsuario(VentasServicio ventasServicio){
        this.ventasServicio = ventasServicio;
    }

    public void start(){
        boolean continuar = true;

        do{
            System.out.println("Seleccione una opción:");
            System.out.println("1. Mostrar datos del cliente");
            System.out.println("2. Mostrar productos disponibles");
            System.out.println("3. Comprar producto");
            System.out.println("4. Salir");

            int opcion  = ValidarInput.leerEntero();
            switch(opcion){
                case 1:
                    mostrarDatosCliente();
                    break;
                case 2:
                    mostrarProductos();
                    break;
                case 3:
                    comprarProducto();
                    break;
                case 4:
                    continuar = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }while(continuar);

    }

    private void mostrarDatosCliente(){
        System.out.println(cliente);
    }

    private void mostrarProductos(){
        for (Producto producto : ventasServicio.obtenerProductos().values()) {
            System.out.println(producto);
        }
    }

    public void comprarProducto(){
        List<DetalleVenta> detallesVenta = new java.util.ArrayList<>();
        boolean continuar = true;

        do{
            System.out.println("Ingresa el ID del producto:");
            int idProducto = ValidarInput.leerEntero();
            Producto producto = ventasServicio.obtenerProducto(idProducto);

            if(producto != null){
                System.out.println(producto);

                if(ventasServicio.verificarProductoEnLista(producto,detallesVenta)){
                    System.out.println("El producto ya fue agregado a la compra.");
                }else {

                    boolean cantidadValida = false;
                    while(!cantidadValida){
                        System.out.println("Ingresa la cantidad a comprar:");
                        int cantidad = ValidarInput.leerEntero();

                        cantidadValida = ventasServicio.agregarProductoAVenta(producto,cantidad,detallesVenta);
                    }

                    System.out.println("¿Deseas agregar otro producto? (s/n)");
                    String respuesta = ValidarInput.leerSiNo();
                    if(respuesta.equalsIgnoreCase("n")){
                        continuar = false;
                    }
                }

            }else{
                System.out.println("Producto no encontrado.");
            }
        }while(continuar);

        Venta nuevaVenta = ventasServicio.generarVenta(detallesVenta, cliente.getId());
        System.out.println(nuevaVenta);
        for(DetalleVenta dv: nuevaVenta.getProductosVendidos()){
            System.out.println(dv.getProducto());
        }

    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
