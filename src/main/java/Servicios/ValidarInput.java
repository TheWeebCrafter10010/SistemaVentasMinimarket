package Servicios;

import java.util.Scanner;

public class ValidarInput {
    private static Scanner sc = new Scanner(System.in);
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
}
