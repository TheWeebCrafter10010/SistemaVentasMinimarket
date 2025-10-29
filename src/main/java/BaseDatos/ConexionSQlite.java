package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQlite {
    private static final String RUTA_DB = "jdbc:sqlite:database/estacionamiento.db";

    private static Connection conexion;

    public static Connection getConnection() {

        if (conexion == null) {
            try {
                conexion = DriverManager.getConnection(RUTA_DB);
                System.out.println("Conexión exitosa.");

            } catch (SQLException e) {
                System.out.println("Error al conectar: " + e.getMessage());
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }


        }
    }
}
