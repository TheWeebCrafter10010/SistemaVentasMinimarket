package Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaFormato {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String fechaHoraActual() {
        LocalDateTime fecha = LocalDateTime.now();
        return fecha.format(formatter);
    }
}
