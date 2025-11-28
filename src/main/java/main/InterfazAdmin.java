package main;

import BaseDatos.IProductosDAO;
import BaseDatos.IVentasDAO;
import BaseDatos.IUsuarioDAO;
import Entidades.*;
import Servicios.Validacion.ProductoValidator;
import Utils.DatosFormater;
import Utils.ValidarInput;

import java.util.List;
import java.util.Scanner;

public class InterfazAdmin {

    private IProductosDAO productosDAO;
    private IUsuarioDAO usuarioDAO;
    private IVentasDAO ventasDAO;
    private Scanner sc = new Scanner(System.in);
    private Admin admin;

    public InterfazAdmin(IProductosDAO productosDAO, IUsuarioDAO usuarioDAO, IVentasDAO ventasDAO) {
        this.productosDAO = productosDAO;
        this.usuarioDAO = usuarioDAO;
        this.ventasDAO = ventasDAO;
    }

    // ====================== MENÚ PRINCIPAL ======================

    public void start() {
        boolean seguir = true;
        do {
            System.out.println("\n======================================");
            System.out.println("          PANEL DE ADMIN");
            System.out.println("======================================");
            System.out.println("1. 👥 Ver clientes");
            System.out.println("2. 📦 Gestión de productos");
            System.out.println("3. 💰 Ver ventas");
            System.out.println("4. \uD83D\uDD14 Ver notificaciones");
            System.out.println("5. 🚪 Cerrar sesión");
            System.out.println("======================================");
            System.out.print("Seleccione una opción: ");

            int opcion = ValidarInput.leerEntero();
            switch (opcion) {
                case 1 -> mostrarClientes();
                case 2 -> menuProductos();
                case 3 -> mostrarVentas();
                case 4 -> mostrarNotificaciones();
                case 5 -> {
                    seguir = false;
                    System.out.println("Sesión admin cerrada.");
                }
                default -> System.out.println("Opción inválida.");
            }
        } while (seguir);
    }

    private void mostrarClientes() {
        DatosFormater.mostrarClientes(usuarioDAO.obtenerClientes());
        ValidarInput.pausa();
    }

    private void menuProductos() {
        boolean seguir = true;
        ProductoValidator validator = new ProductoValidator();

        do {
            System.out.println("\n================ GESTIÓN DE PRODUCTOS ================");
            System.out.println("1. Ver productos");
            System.out.println("2. Editar producto");
            System.out.println("3. Agregar producto");
            System.out.println("4. Volver");
            System.out.print("Seleccione opción: ");

            int op = ValidarInput.leerEntero();

            switch (op) {
                case 1 -> mostrarProductos();
                case 2 -> editarProducto(validator);
                case 3 -> agregarProducto(validator);
                case 4 -> seguir = false;
                default -> System.out.println("Opción inválida.");
            }
        } while (seguir);
    }

    private void mostrarProductos() {
        DatosFormater.mostrarProductos(productosDAO.obtenerProductos());
    }

    private void editarProducto(ProductoValidator validator) {
        System.out.print("\nIngrese ID del producto: ");
        int id = ValidarInput.leerEntero();

        Producto p = productosDAO.obtenerProductoPorID(id);
        if (p == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        System.out.println("Producto actual:");
        System.out.printf("Nombre: %s | Precio: S/. %.2f | Stock: %d%n",
                p.getNombre(), p.getPrecio(), p.getStock());

        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = ValidarInput.leerString();
        if (nombre.isBlank()) nombre = p.getNombre();

        System.out.print("Nuevo precio (0 para mantener): ");
        double precio = ValidarInput.leerDouble();
        if (precio == 0) precio = p.getPrecio();

        System.out.print("Nuevo stock (-1 para mantener): ");

        int stock = ValidarInput.leerEntero();
        if (stock == -1) stock = p.getStock();

        var res = validator.validar(nombre, precio, stock);
        if (!res.valido) {
            System.out.println("Error: " + res.mensaje);
            return;
        }

        Producto actualizado = new Producto.Builder()
                .setID(p.getId())
                .setNombre(nombre)
                .setPrecio(precio)
                .setStock(stock)
                .build();

        productosDAO.actualizarProducto(actualizado);
        System.out.println("✅ Producto actualizado correctamente.");
    }

    private void agregarProducto(ProductoValidator validator) {
        System.out.print("Nombre: ");
        String nombre = ValidarInput.leerString();

        System.out.print("Precio: ");
        double precio = ValidarInput.leerDouble();

        System.out.print("Stock: ");
        int stock = ValidarInput.leerEntero();

        var res = validator.validar(nombre, precio, stock);
        if (!res.valido) {
            System.out.println("Error: " + res.mensaje);
            return;
        }

        Producto nuevo = new Producto.Builder()
                .setNombre(nombre)
                .setPrecio(precio)
                .setStock(stock)
                .build();

        int id = productosDAO.insertarProducto(nuevo);
        System.out.println("✅ Producto registrado con ID " + id);
    }

    private void mostrarVentas() {
        DatosFormater.mostrarVentas(ventasDAO.obtenerVentas());
        System.out.println("Ingrese la ID de venta para ver el ticket de venta o 0 para volver: ");

        int idVenta = ValidarInput.leerEntero();
        if (idVenta == 0) return;
        Venta venta = ventasDAO.obtenerVentaPorID(idVenta);
        if (venta != null) {
            System.out.println(venta);
        } else {
            System.out.println("Venta no encontrada.");
        }
        ValidarInput.pausa();
    }

    private void mostrarNotificaciones() {
        List<String> notificaciones = admin.getNotificaciones();
        notificaciones.forEach(System.out::println);
        ValidarInput.pausa();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
