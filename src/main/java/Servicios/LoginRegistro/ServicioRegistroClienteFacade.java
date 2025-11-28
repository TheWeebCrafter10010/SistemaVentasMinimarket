package Servicios.LoginRegistro;

import BaseDatos.IUsuarioDAO;
import Servicios.Validacion.ValidacionRegistro;
import Entidades.Cliente;
import Servicios.Validacion.ServicioValidacionUsuario;

public class ServicioRegistroClienteFacade {
    private ServicioValidacionUsuario servicioRegistro;
    private IUsuarioDAO usuarioDAO;

    public ServicioRegistroClienteFacade(ServicioValidacionUsuario servicioRegistro, IUsuarioDAO usuarioDAO) {
        this.servicioRegistro = servicioRegistro;
        this.usuarioDAO = usuarioDAO;
    }

    public ValidacionRegistro registrarCliente(Cliente cliente){
        ValidacionRegistro validacion = new ValidacionRegistro();
        validacion.emailFormato = servicioRegistro.verificarFormatoEmail(cliente.getEmail());
        validacion.pwdFormato = servicioRegistro.verificarPwd(cliente.getPwd());
        validacion.telefonoFormato = servicioRegistro.verificarTelefono(cliente.getTelefono());
        validacion.emailUnico = servicioRegistro.verificarEmailUnico(cliente.getEmail());

        if(validacion.esValido()){
            usuarioDAO.insertarCliente(cliente);
        }
        return validacion;

    }

}
