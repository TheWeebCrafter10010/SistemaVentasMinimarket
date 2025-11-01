package Mocks;

import BaseDatos.IClienteDAO;
import Entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAOMock implements IClienteDAO {

    private List<Cliente> clientes;
    private int idCounter = 1;

    public ClienteDAOMock() {
        clientes = new ArrayList<>();

        // Datos de ejemplo iniciales
        clientes.add(new Cliente(idCounter++, "Alonso", "Benito", "alonso123@gmail.com", "123456", "999888777", new ArrayList<>()));
        clientes.add(new Cliente(idCounter++, "Bryan", "Torres", "bryan@gmail.com", "abcdef", "988776655", new ArrayList<>()));
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
    public void insertarNuevoCliente(Cliente cliente) {
        cliente.setId(idCounter++); // Asignar ID automáticamente
        clientes.add(cliente);
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
