package Servicios;

import DataTransferObjects.ValidacionRegistro;
import Entidades.Cliente;

public class ServicioRegistroClienteFacade {
    private ServicioValidacionUsuario servicioRegistro;

    public ServicioRegistroClienteFacade(ServicioValidacionUsuario servicioRegistro) {
        this.servicioRegistro = servicioRegistro;
    }

    public ValidacionRegistro validarCampos(Cliente cliente) {
        //Se debe pasar un cliente ya creado con sus campos llenos

        ValidacionRegistro validacion = new ValidacionRegistro();
        validacion.emailFormato = servicioRegistro.verificarFormatoEmail(cliente.getEmail());
        validacion.pwdFormato = servicioRegistro.verificarPwd(cliente.getPwd());
        validacion.telefonoFormato = servicioRegistro.verificarTelefono(cliente.getTelefono());

        return  validacion;
    }

    public boolean verificarEmailUnico(String email) {
        //Usar despues de validar los campos para evitar consultas innecesarias
        return servicioRegistro.verificarEmailUnico(email);
    }

    public void registrarNuevoCliente(Cliente cliente) {
        //Usar despues de haber hecho todas las validaciones necesarias
        servicioRegistro.registrarNuevoCliente(cliente);
    }


}
