package Mocks;
import BaseDatos.ColumnaProducto;
import BaseDatos.IProductosDAO;
import Entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductosDAOMock implements IProductosDAO {

    private List<Producto> productos = new ArrayList<>();

    public ProductosDAOMock() {
        // Datos de ejemplo
        productos.add(new Producto(1, "Laptop Lenovo", 2500.0, 10));
        productos.add(new Producto(2, "Laptop HP", 2600.0, 8));
        productos.add(new Producto(3, "Laptop Dell", 2700.0, 6));
        productos.add(new Producto(4, "Mouse Logitech", 80.0, 25));
        productos.add(new Producto(5, "Mouse Redragon", 75.0, 20));
        productos.add(new Producto(6, "Mouse Microsoft", 85.0, 18));
        productos.add(new Producto(7, "Teclado Redragon", 150.0, 15));
        productos.add(new Producto(8, "Teclado Logitech", 180.0, 12));
        productos.add(new Producto(9, "Monitor LG 24", 600.0, 9));
        productos.add(new Producto(10, "Monitor Samsung 27", 850.0, 7));
        productos.add(new Producto(11, "Cámara Logitech", 200.0, 14));
        productos.add(new Producto(12, "Cámara Sony", 320.0, 11));
        productos.add(new Producto(13, "Cargador Lenovo", 90.0, 30));
        productos.add(new Producto(14, "Cargador HP", 95.0, 25));
        productos.add(new Producto(15, "Cargador Dell", 100.0, 22));
    }

    @Override
    public Producto obtenerProductoPorID(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public List<Producto> obtenerProductosPorNombre(String cadenaNombre) {
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(cadenaNombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public int insertarProducto(Producto producto) {
        productos.add(producto);
        return productos.size()-1; // Retorna el ID generado (índice en la lista)
    }

    @Override
    public void actualizarProducto(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getId() == producto.getId()) {
                productos.set(i, producto);
                return;
            }
        }
    }

    public void actualizarStockProducto(int idProducto, int nuevoStock) {
        for (Producto p : productos) {
            if (p.getId() == idProducto) {
                p.setStock(nuevoStock);
                break;
            }
        }
    }

    @Override
    public boolean actualizarColumnaProducto(int idProducto, ColumnaProducto columna, Object nuevoValor) {
        columna.getTipo();
        if(!columna.getTipo().isInstance(nuevoValor)) {
            System.out.println("El tipo de dato no coincide con el de la columna");
            return false;
        }
        Producto p = obtenerProductoPorID(idProducto);

        if (p != null) {
            switch (columna) {
                case STOCK:
                    p.setStock((Integer) nuevoValor);
                    break;
                case PRECIO:
                    p.setPrecio((Double) nuevoValor);
                    break;
                case IMGURL:
                    p.setImgURL((String) nuevoValor);
                    break;
            }

        }
        return true;
    }
}

