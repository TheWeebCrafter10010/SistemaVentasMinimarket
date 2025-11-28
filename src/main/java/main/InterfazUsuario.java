package main;

import Entidades.Cliente;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;
import Servicios.ValidarInput;
import Servicios.VentasServicio;

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
                    pausa();
                    break;
                case 2:
                    mostrarProductos();
                    pausa();
                    break;
                case 3:
                    comprarProducto();
                    pausa();
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
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           DATOS DEL CLIENTE");
        System.out.println("=".repeat(50));
        System.out.printf("%-15s : %s%n", "ID", cliente.getId());
        System.out.printf("%-15s : %s %s%n", "Cliente", cliente.getNombre(), cliente.getApellido());
        System.out.printf("%-15s : %s%n", "Email", cliente.getEmail());
        System.out.printf("%-15s : %s%n", "Teléfono", cliente.getTelefono());
        System.out.printf("%-15s : %s%n", "Dirección", cliente.getDireccion());
        System.out.println("=".repeat(50));
    }

    // ================= LISTA PRODUCTOS ==================
    private void mostrarProductos() {

        List<Producto> lista = new ArrayList<>(ventasServicio.obtenerProductos().values());

        System.out.println("\n================ PRODUCTOS DISPONIBLES ================");
        System.out.printf("%-4s %-25s %-12s %-8s%n", "ID", "PRODUCTO", "PRECIO", "STOCK");
        System.out.println("-------------------------------------------------------");

        for (Producto p : lista) {
            System.out.printf("%-4d %-25s S/. %-8.2f %-8d%n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock());
        }

        System.out.println("=======================================================");
    }

    // ================= COMPRA ==================
    private void comprarProducto(){

        List<DetalleVenta> detallesVenta = new ArrayList<>();
        boolean continuar = true;

        System.out.println("\nIniciando compra...\n");

        while (continuar){

            mostrarProductos();

            System.out.print("Ingrese ID del producto: ");
            int idProducto = ValidarInput.leerEntero();

            Producto producto = ventasServicio.obtenerProducto(idProducto);

            if(producto == null){
                System.out.println("Producto no encontrado.");
                continue;
            }

            if(ventasServicio.verificarProductoEnLista(producto,detallesVenta)){
                System.out.println("Producto ya fue agregado a su compra.");
                continue;
            }

            System.out.print("Cantidad a comprar: ");
            int cantidad = ValidarInput.leerEntero();

            if(ventasServicio.agregarProductoAVenta(producto, cantidad, detallesVenta)){
                System.out.println("Producto agregado correctamente.");
            }else{
                System.out.println("Stock insuficiente.");
            }

            System.out.print("¿Desea agregar otro producto? (s/n): ");
            String respuesta = ValidarInput.leerSiNo();
            continuar = !respuesta.equalsIgnoreCase("n");
        }

        Venta nuevaVenta = ventasServicio.generarVenta(detallesVenta, cliente.getId());

        mostrarTicket(nuevaVenta);
    }

    // ================= TICKET ==================
    private void mostrarTicket(Venta venta) {

        System.out.println("\n===================== BOLETA =====================");
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Fecha: " + venta.getFechaConFormato());
        System.out.println("--------------------------------------------------");

        System.out.printf("%-20s %-8s %-8s%n", "PRODUCTO", "CANT", "SUBTOTAL");

        for (DetalleVenta dv : venta.getProductosVendidos()) {
            System.out.printf("%-20s %-8d S/. %-7.2f%n",
                    dv.getProducto().getNombre(),
                    dv.getCantidad(),
                    dv.getTotalDetalleVenta());
        }

        System.out.println("--------------------------------------------------");
        System.out.println("TOTAL A PAGAR: S/. " + venta.getTotal());
        System.out.println("==================================================");
    }

    // ================= UTIL ==================
    private void pausa(){
        System.out.println("\nPresione ENTER para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
