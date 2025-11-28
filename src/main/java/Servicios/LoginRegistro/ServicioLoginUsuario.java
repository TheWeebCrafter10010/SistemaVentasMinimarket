package Servicios.LoginRegistro;

import BaseDatos.IUsuarioDAO;
import Entidades.Usuario;
import Servicios.Validacion.ServicioValidacionUsuario;

public class ServicioLoginUsuario {

    private ServicioValidacionUsuario validacion;
    private IUsuarioDAO usuarioDAO;

    public ServicioLoginUsuario(ServicioValidacionUsuario validacion, IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.validacion = validacion;
    }

    public boolean verificarFormatoValido(String email, String pwd) {
        //Verifica que el formato del email y la contraseña sean validos
        //Evitando consultas innecesarias
        boolean emailValido = validacion.verificarFormatoEmail(email);
        boolean pwdValida = validacion.verificarPwd(pwd);
        return emailValido && pwdValida;
    }

    public Usuario loginUsuario(String email, String pwd) {
        //Usar despues de verificar que el formato es valido
        //Puede devolver un Admin o un Cliente
        return usuarioDAO.obtenerUsuarioLogin(email,pwd);
    }

}
