package main;

import BaseDatos.IProductosDAO;
import Entidades.DetalleVenta;
import Entidades.Producto;
import Entidades.Venta;;
import Servicios.VentasServicio;

import java.util.List;
import java.util.Scanner;

public class InterfazConsolaPrueba {
    VentasServicio ventasServicio;
    IProductosDAO productosDAO;
    Scanner sc = new Scanner(System.in);

    public InterfazConsolaPrueba(IProductosDAO productosDAO, VentasServicio ventasServicio) {
        this.productosDAO = productosDAO;
        this.ventasServicio = ventasServicio;
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

        Venta nuevaVenta = ventasServicio.generarVenta(detallesVenta);
        System.out.println(nuevaVenta);
        for(DetalleVenta dv: nuevaVenta.getProductosVendidos()){
            System.out.println(dv.getProducto());
        }

    }

}
