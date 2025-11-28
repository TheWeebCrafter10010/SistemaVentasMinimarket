package main;

import BaseDatos.IProductosDAO;
import BaseDatos.IVentasDAO;
import BaseDatos.IUsuarioDAO;
import Entidades.Cliente;
import Entidades.Producto;
import Entidades.Venta;
import Servicios.ProductoValidator;
import Servicios.ValidarInput;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InterfazAdmin {

    private IProductosDAO productosDAO;
    private IUsuarioDAO usuarioDAO;
    private IVentasDAO ventasDAO;
    private Scanner sc = new Scanner(System.in);

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
            System.out.println("4. 🚪 Cerrar sesión");
            System.out.println("======================================");
            System.out.print("Seleccione una opción: ");

            int opcion = ValidarInput.leerEntero();
            switch (opcion) {
                case 1 -> mostrarClientes();
                case 2 -> menuProductos();
                case 3 -> mostrarVentas();
                case 4 -> {
                    seguir = false;
                    System.out.println("Sesión admin cerrada.");
                }
                default -> System.out.println("Opción inválida.");
            }
        } while (seguir);
    }

    // ====================== CLIENTES ======================

    private void mostrarClientes() {
        System.out.println("\n================ LISTA DE CLIENTES ================");
        System.out.printf("%-4s %-15s %-15s %-25s %-12s%n",
                "ID", "NOMBRE", "APELLIDO", "EMAIL", "TIPO");
        System.out.println("---------------------------------------------------");

        for (Object o : usuarioDAO.obtenerClientes().values()) {
            if (o instanceof Cliente c) {
                System.out.printf("%-4d %-15s %-15s %-25s %-12s%n",
                        c.getId(),
                        c.getNombre(),
                        c.getApellido(),
                        c.getEmail(),
                        "CLIENTE"
                );
            }
        }

        pausa();
    }

    // ====================== PRODUCTOS ======================

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
        System.out.println("\n================ LISTA DE PRODUCTOS ================");
        System.out.printf("%-4s %-25s %-10s %-8s%n",
                "ID", "PRODUCTO", "PRECIO", "STOCK");
        System.out.println("----------------------------------------------------");

        for (Producto p : productosDAO.obtenerProductos().values()) {
            System.out.printf("%-4d %-25s S/. %-7.2f %-8d%n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            );
        }

        pausa();
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
        String nombre = sc.nextLine();
        if (nombre.isBlank()) nombre = p.getNombre();

        System.out.print("Nuevo precio (0 para mantener): ");
        double precio = readDoubleAllowZero();
        if (precio == 0) precio = p.getPrecio();

        System.out.print("Nuevo stock (-1 para mantener): ");
        int stock = readIntAllowMinusOne();
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
        String nombre = sc.nextLine();

        System.out.print("Precio: ");
        double precio = readDoubleStrict();

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

    // ====================== VENTAS ======================

    private void mostrarVentas() {
        System.out.println("\n================ HISTORIAL DE VENTAS ================");
        System.out.printf("%-4s %-20s %-8s %-15s%n",
                "ID", "CLIENTE", "TOTAL", "FECHA");
        System.out.println("----------------------------------------------------");

        try {
            List<Venta> ventas = (List<Venta>) ventasDAO
                    .getClass()
                    .getMethod("getVentas")
                    .invoke(ventasDAO);

            for (Venta v : ventas) {
                System.out.printf("%-4d %-20s S/. %-5.2f %-15s%n",
                        v.getID(),
                        obtenerNombreCliente(v.getID()),
                        v.getTotal(),
                        v.getFecha()
                );
            }

        } catch (Exception e) {
            Venta ultima = ventasDAO.obtenerUltimaVenta();
            if (ultima != null) {
                System.out.printf("%-4d %-20s S/. %-5.2f %-15s%n",
                        ultima.getID(),
                        obtenerNombreCliente(ultima.getID()),
                        ultima.getTotal(),
                        ultima.getFecha()
                );
            } else {
                System.out.println("No hay ventas registradas.");
            }
        }

        pausa();
    }

    // ====================== UTILIDADES ======================

    private String obtenerNombreCliente(int id) {
        for (Object o : usuarioDAO.obtenerClientes().values()) {
            if (o instanceof Cliente c && c.getId() == id) {
                return c.getNombre() + " " + c.getApellido();
            }
        }
        return "Desconocido";
    }

    private void pausa() {
        System.out.println("\nPresione Enter para volver...");
        sc.nextLine();
    }

    private double readDoubleAllowZero() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private double readDoubleStrict() {
        while (true) {
            try {
                double n = Double.parseDouble(sc.nextLine());
                if (n > 0) return n;
                System.out.print("Debe ser mayor que 0: ");
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private int readIntAllowMinusOne() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Ingrese un número válido (-1 para mantener): ");
            }
        }
    }
}
