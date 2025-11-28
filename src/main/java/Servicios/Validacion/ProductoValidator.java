package Servicios.Validacion;

public class ProductoValidator {

    public static class ResultadoValidacion {
        public boolean valido;
        public String mensaje;
        public ResultadoValidacion(boolean v, String m) { valido = v; mensaje = m; }
    }

    public ResultadoValidacion validar(String nombre, double precio, int stock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ResultadoValidacion(false, "Nombre inválido: no puede estar vacío.");
        }
        if (precio <= 0) {
            return new ResultadoValidacion(false, "Precio inválido: debe ser mayor a 0.");
        }
        if (stock < 0) {
            return new ResultadoValidacion(false, "Stock inválido: debe ser >= 0.");
        }
        return new ResultadoValidacion(true, "OK");
    }
}
