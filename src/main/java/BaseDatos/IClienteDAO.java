package BaseDatos;

import Entidades.Cliente;
import java.util.List;

public interface IClienteDAO {

    Cliente obtenerClienteLogin(String email, String pwd);

    boolean emailYaRegistrado(String email);

    boolean insertarCliente(Cliente cliente); // ✅ cambio aquí

    List<Cliente> obtenerClientes(int limite);
}
