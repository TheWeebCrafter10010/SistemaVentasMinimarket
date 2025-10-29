package BaseDatos;

public enum ColumnaProducto {
    //Enum de columnas que se pueden actualizar en la tabla Producto
    STOCK("stock",Integer.class),
    PRECIO("precio",Double.class),
    IMGURL("imgUrl",String.class);

    private final String columna;
    private final Class<?> tipo;

    private ColumnaProducto(String columna,Class<?> tipo) {
        this.columna = columna;
        this.tipo = tipo;
    }
    public String getColumna() {
        return columna;
    }
    public Class<?> getTipo(){
        return tipo;
    }
}
