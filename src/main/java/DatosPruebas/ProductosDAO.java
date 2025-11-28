package DatosPruebas;

import BaseDatos.IProductosDAO;
import Entidades.Producto;
import Servicios.stock.StockObserver;
import Servicios.stock.StockSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosDAO implements IProductosDAO, StockSubject {

    private HashMap<Integer, Producto> productos;
    private int contadorIDs = 20; // inicia > últimos IDs que ya tenías (evita colisiones)
    private List<StockObserver> observers;
    private final int UMBRAL_STOCK_BAJO = 10; // umbral configurable

    public ProductosDAO(){
        productos = new HashMap<>();
        observers = new ArrayList<>();
        // --- productos iniciales (como los tenías) ---
        productos.put(1, new Producto(1,"Arroz Extra 5kg",24.5,120));
        productos.put(2, new Producto(2,"Azúcar Rubia 1kg",4.8,200));
        productos.put(3, new Producto(3,"Aceite Vegetal 1L",9.9,150));
        productos.put(4, new Producto(4,"Fideos Spaghetti 500g",3.5,180));
        productos.put(5, new Producto(5,"Conserva de Atún 170g",6.2,90));
        productos.put(6, new Producto(6,"Leche Evaporada 400ml",4.1,250));
        productos.put(7, new Producto(7,"Gaseosa 1.5L",7.5,100));
        productos.put(8, new Producto(8,"Agua Mineral 625ml",2.5,180));
        productos.put(9, new Producto(9,"Pan de Molde 500g",8.4,70));
        productos.put(10, new Producto(10,"Detergente en Polvo 1kg",9.8,130));
        productos.put(11, new Producto(11,"Papel Higiénico 4 unidades",5.9,160));
        productos.put(12, new Producto(12,"Sal de Mesa 1kg",2.0,100));
        productos.put(13, new Producto(13,"Café Instantáneo 100g",11.5,50));
        productos.put(14, new Producto(14,"Mermelada de Fresa 250g",7.2,80));
        productos.put(15, new Producto(15,"Salsa de Tomate 200g",3.1,90));
        productos.put(16, new Producto(16,"Leche Gloria",4.5,50));
        productos.put(17, new Producto(17,"Arroz Costeño 1kg",3.2,80));
        productos.put(18, new Producto(18,"Azúcar Rubia 1kg",2.8,60));
        productos.put(19, new Producto(19,"Detergente Ace 1kg",6.9,70));
        // contadorIDs ya en 20, la próxima inserción recibirá id 20, 21...
    }

    @Override
    public Producto obtenerProductoPorID(int id) {
        return productos.get(id);
    }

    @Override
    public Map<Integer, Producto> obtenerProductos() {
        return productos;
    }

    @Override
    public int insertarProducto(Producto producto) {
        int nuevoID = contadorIDs++;
        // Clon minimal: crear nuevo producto con ID asignado (si usas Builder)
        Producto productoConID = new Producto.Builder()
                .setID(nuevoID)
                .setNombre(producto.getNombre())
                .setPrecio(producto.getPrecio())
                .setStock(producto.getStock())
                .build();
        productos.put(nuevoID, productoConID);
        // Notificar si stock bajo
        if (productoConID.getStock() <= UMBRAL_STOCK_BAJO) {
            notifyObservers("Nuevo producto agregado con stock bajo: " + productoConID.getNombre() + " (stock=" + productoConID.getStock() + ")");
        } else {
            notifyObservers("Nuevo producto agregado: " + productoConID.getNombre() + " (stock=" + productoConID.getStock() + ")");
        }
        return nuevoID;
    }

    @Override
    public void actualizarProducto(Producto producto) {
        if (producto == null) return;
        if (productos.containsKey(producto.getId())) {
            productos.put(producto.getId(), producto);
            if (producto.getStock() <= UMBRAL_STOCK_BAJO) {
                notifyObservers("Stock bajo tras actualización: " + producto.getNombre() + " (stock=" + producto.getStock() + ")");
            } else {
                notifyObservers("Producto actualizado: " + producto.getNombre() + " (stock=" + producto.getStock() + ")");
            }
        }
    }

    // StockSubject methods
    @Override
    public void attach(StockObserver observer) {
        if (!observers.contains(observer)) observers.add(observer);
    }

    @Override
    public void detach(StockObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String mensaje) {
        for (StockObserver o : observers) {
            try {
                o.actualizarStock(mensaje);
            } catch (Exception ignored) {}
        }
    }
}
