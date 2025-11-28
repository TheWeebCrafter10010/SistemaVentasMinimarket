package main;

import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;
import Utils.DatosFormater;
import Utils.ValidarInput;
import Servicios.Ventas.VentasServicio;

import java.util.ArrayList;
import java.util.List;

public class InterfazUsuario {

    private VentasServicio ventasServicio;
    private Cliente cliente;

    public InterfazUsuario(VentasServicio ventasServicio){
        this.ventasServicio = ventasServicio;
    }

    public void start(){
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();

            int opcion = ValidarInput.leerEntero();

            switch(opcion){
                case 1:
                    mostrarDatosCliente();
                    ValidarInput.pausa();
                    break;
                case 2:
                    mostrarProductos();
                    ValidarInput.pausa();
                    break;
                case 3:
                    comprarProducto();
                    ValidarInput.pausa();
                    break;
                case 4:
                    continuar = false;
                    System.out.println("\nGracias por su visita. ¡Vuelva pronto!\n");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // ================= MENU ==================
    private void mostrarMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        MINI MARKET - MENÚ CLIENTE");
        System.out.println("=".repeat(50));
        System.out.println("1. Ver datos personales");
        System.out.println("2. Ver productos disponibles");
        System.out.println("3. Realizar compra");
        System.out.println("4. Salir");
        System.out.println("-".repeat(50));
        System.out.print("Seleccione una opción: ");
    }

    // ================= DATOS CLIENTE ==================
    private void mostrarDatosCliente(){
        System.out.println(cliente.toString());
        System.out.println("Desea ver sus compras realizadas? (s/n): ");
        String respuesta = ValidarInput.leerSiNo();

        if(respuesta.equalsIgnoreCase("s")){
            DatosFormater.mostrarVentas(cliente.getCompras());
            System.out.println("Ingrese la ID de una venta para ver detalles o 0 para salir: ");
            int idVenta = ValidarInput.leerEntero();
            if(idVenta != 0){
                List<Venta> ventasCliente = cliente.getCompras();
                Venta venta = ventasCliente.stream().filter(v -> v.getID() == idVenta).findFirst().orElse(null);
                if(venta != null){
                    System.out.println(venta);
                }else{
                    System.out.println("Venta no encontrada.");
                }
            }
        }
    }

    // ================= LISTA PRODUCTOS ==================
    private void mostrarProductos() {
        DatosFormater.mostrarProductos(ventasServicio.obtenerProductos());
    }

    // ================= COMPRA ==================
    private void comprarProducto(){

        List<DetalleVenta> detallesVenta=  new ArrayList<>();;
        boolean continuar = true;


        System.out.println("\nIniciando compra...\n");

        while (continuar){

            mostrarProductos();

            System.out.print("Ingrese ID del producto: ");
            int idProducto = ValidarInput.leerEntero();

            Producto producto = ventasServicio.obtenerProducto(idProducto);

            if(producto == null){
                System.out.println("Producto no encontrado.");
            }else{

                if(!ventasServicio.verificarProductoEnLista(producto,detallesVenta)){
                    System.out.print("Cantidad a comprar: ");
                    int cantidad = ValidarInput.leerEntero();
                    boolean agregado = ventasServicio.agregarProductoAVenta(producto, cantidad, detallesVenta);

                    if(agregado){
                        System.out.println("Producto agregado correctamente.");
                    }else{
                        System.out.println("Stock insuficiente.");
                    }

                }else {
                    System.out.println("Producto ya fue agregado a su compra.");
                    continue;
                }
            }

            System.out.print("¿Desea agregar otro producto? (s/n): ");
            String respuesta = ValidarInput.leerSiNo();
            continuar = !respuesta.equalsIgnoreCase("n");

        }
        if(!detallesVenta.isEmpty()){
            Venta nuevaVenta = ventasServicio.generarVenta(detallesVenta, this.cliente);
            System.out.println(nuevaVenta);
        }else{
            System.out.println("No se agregaron productos a la compra.");
        }

    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
