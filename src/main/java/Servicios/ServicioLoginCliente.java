package Servicios;

import BaseDatos.IClienteDAO;
import Entidades.Cliente;

public class ServicioLoginCliente {

    private ServicioValidacionCliente validacion;
    private IClienteDAO clienteDAO;

    public ServicioLoginCliente(ServicioValidacionCliente validacion, IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        this.validacion = validacion;
    }

    public boolean verificarFormatoValido(String email, String pwd) {
        //Verifica que el formato del email y la contraseña sean validos
        //Evitando consultas innecesarias a la base de datos
        boolean emailValido = validacion.verificarFormatoEmail(email);
        boolean pwdValida = validacion.verificarPwd(pwd);
        return emailValido && pwdValida;
    }

    public Cliente loginCliente(String email, String pwd) {
        //Usar despues de verificar que el formato es valido
        return clienteDAO.obtenerClienteLogin(email,pwd);
    }

}
