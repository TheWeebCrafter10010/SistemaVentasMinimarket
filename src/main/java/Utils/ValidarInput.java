package Utils;

import java.util.Scanner;

public class ValidarInput {
    private static Scanner sc = new Scanner(System.in);

    public static String leerString(){
        return sc.nextLine();
    }

    public static int leerEntero() {

        do{
            try {
                int num = sc.nextInt();
                sc.nextLine();
                return num;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor ingrese un número entero:");
                sc.nextLine(); // Limpiar el buffer
            }
        }while(true);

    }

    public static String leerSiNo(){
        String entrada;
        do{
            entrada = sc.nextLine().trim().toLowerCase();
            if(entrada.equals("s") || entrada.equals("n")){
                return entrada;
            }else{
                System.out.println("Entrada inválida. Por favor ingrese 's' para sí o 'n' para no:");
            }
        }while(true);
    }

    public static double leerDouble(){
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Entrada invalida, Por favor ingrese un número entero/decimal: ");
            }
        }
    }

    public static void pausa(){
        System.out.println("\nPresione ENTER para continuar...");
        sc.nextLine();
    }
}
