package main;

import BaseDatos.*;
import Entidades.Cliente;
import Entidades.Producto;
import Entidades.Usuario;

import java.util.List;

public class pruebaMain {
    public static void main(String[] args) {
        InterfazLogin interfaz = SistemaContexto.crearInterfaz();
        interfaz.iniciar();

            
    }
}
