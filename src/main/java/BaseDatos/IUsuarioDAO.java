package BaseDatos;

import Entidades.Cliente;
import Entidades.Usuario;

import java.util.List;

public interface IUsuarioDAO {

    //Retorna un usuario, puede ser Cliente o Admin
    //Hacer casting despues de usar este metodo para elegir la interfaz correcta Admin/Cliente
    Usuario obtenerUsuarioLogin(String email, String pwd);

    boolean emailYaRegistrado(String email);

    boolean insertarCliente(Cliente cliente);

    List<Cliente> obtenerClientes(int limite);

    boolean insertarAccionAdmin(int idAdmin,String accion,String fecha);
}
