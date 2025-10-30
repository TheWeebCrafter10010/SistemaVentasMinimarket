package BaseDatos;
import Entidades.Cliente;

import java.util.List;

public interface IClienteDAO {

    Cliente obtenerClienteLogin(String email,String pwd);

    boolean existeClientePorEmail(String email);

    void insertarNuevoCliente(Cliente cliente);

    List<Cliente> obtenerClientes(int limite);//Limite de clientes a obtener
}
