package Utils;

import Entidades.Cliente;
import Entidades.Producto;
import Entidades.Usuario;
import Entidades.Venta;

import java.util.List;
import java.util.Map;

public class DatosFormater {

    public static void mostrarClientes(Map<String, Usuario> datosClientes) {
        System.out.println("\n================ LISTA DE CLIENTES ================");
        System.out.printf("%-4s %-15s %-15s %-25s %-12s%n",
                "ID", "NOMBRE", "APELLIDO", "EMAIL", "TIPO");
        System.out.println("---------------------------------------------------");

        for (Usuario o : datosClientes.values()) {
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
    }

    public static void mostrarProductos(Map<Integer, Producto> datosProductos) {
        System.out.println("\n================ LISTA DE PRODUCTOS ================");
        System.out.printf("%-4s %-30s %-10s %-8s%n",
                "ID", "PRODUCTO", "PRECIO", "STOCK");
        System.out.println("----------------------------------------------------");

        for (Producto p : datosProductos.values()) {
            System.out.printf("%-4d %-30s S/. %-7.2f %-8d%n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            );
        }
        System.out.println("=======================================================");
    }

    public static void mostrarVentas(List<Venta> ventas  ) {
        if(ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        System.out.printf("%-5s | %-12s | %-15s | %-12s%n",
                "ID", "Fecha", "Cliente", "Total (S/.)");

        System.out.println("----------------------------------------------------------");
        for (Venta v : ventas) {
            System.out.printf("%-5d | %-12s | %-15s | %-12.2f%n",
                    v.getID(),
                    v.getFechaConFormato(),
                    v.getCliente().getNombre(),
                    v.getTotalVenta());
        }
    }


}
