package Servicios;

import BaseDatos.IClienteDAO;
import Entidades.Cliente;

public class ServicioValidacionCliente {

    private IClienteDAO clienteDAO;

    public ServicioValidacionCliente(IClienteDAO clienteDAO){
        //Inyectar la dependencia del DAO
        this.clienteDAO = clienteDAO;
    }

    public boolean verificarFormatoEmail(String email) {
        //Verficiar que el email tenga un formato valido

        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public boolean verificarPwd(String pwd) {
        //Verificar que la contraseña cumpla con los requisitos minimos
        if (pwd == null || pwd.length() < 6) {
            return false;
        }
        return true;
    }

    public boolean verificarTelefono(String telefono) {
        //Verificar que el numero de telefono tenga un formato valido
        if (telefono == null || telefono.isEmpty()) {
            return false;
        }
        String telefonoRegex = "^[0-9]{7,15}$"; // Solo dígitos, entre 7 y 15 caracteres
        return telefono.matches(telefonoRegex);
    }

    public boolean verificarEmailUnico(String email) {
        //Verificar que el email no exista en la base de datos
        boolean clienteExistente = clienteDAO.existeClientePorEmail(email);
        return !clienteExistente;
    }

    public void registrarNuevoCliente(Cliente cliente) {
        //Registrar un nuevo cliente en la base de datos
        //Usar despues de haber hecho todas las validaciones necesarias
        clienteDAO.insertarNuevoCliente(cliente);
    }
}
