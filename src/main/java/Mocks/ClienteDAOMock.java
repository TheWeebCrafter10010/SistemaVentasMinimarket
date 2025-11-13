package Mocks;

import BaseDatos.IClienteDAO;
import Entidades.Cliente;
import Entidades.Venta;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMock implements IClienteDAO {

    private List<Cliente> clientes;
    private int idCounter = 1;

    public ClienteDAOMock() {
        clientes = new ArrayList<>();

        // Datos de ejemplo iniciales
        Cliente c1 = new Cliente.Builder()
                    .setId(idCounter++)
                    .setNombre("Alonso")
                    .setApellido("Benito")
                    .setEmail("alonso123@gmail.com")
                    .setPwd("123456")
                    .setTelefono("999888777")
                    .buildCliente();
        c1.setCompras(new ArrayList<Venta>());

        Cliente c2 = new Cliente.Builder()
                .setId(idCounter++)
                .setNombre("Bryan")
                .setApellido("Torres")
                .setEmail("bryan@gmail.com")
                .setPwd("abcdef")
                .setTelefono("988776655")
                .buildCliente();
        c2.setCompras(new ArrayList<Venta>());
        clientes.add(c1);
        clientes.add(c2);
    }

    @Override
    public Cliente obtenerClienteLogin(String email, String pwd) {
        for (Cliente c : clientes) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPwd().equals(pwd)) {
                return c;
            }
        }
        return null; // No encontrado o credenciales incorrectas
    }

    @Override
    public boolean emailYaRegistrado(String email) {
        for (Cliente c : clientes) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
public boolean insertarCliente(Cliente cliente) {
    cliente.setId(idCounter++);
    clientes.add(cliente);
    return true;
}


    @Override
    public List<Cliente> obtenerClientes(int limite) {
        if (limite <= 0 || limite >= clientes.size()) {
            return new ArrayList<>(clientes);
            // Retorna todos los clientes si el límite es inválido o mayor que el tamaño de la lista
        }
        return new ArrayList<>(clientes.subList(0, limite));
    }
}