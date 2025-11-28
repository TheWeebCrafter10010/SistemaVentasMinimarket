package Servicios.Validacion;

public class ValidacionRegistro {
    public boolean emailFormato;
    public boolean pwdFormato;
    public boolean telefonoFormato;
    public boolean emailUnico;

    public boolean esValido() {
        return emailFormato && pwdFormato && telefonoFormato&&emailUnico;
    }

    public String mostrarErrores() {
        StringBuilder errores = new StringBuilder();
        if (!emailFormato) {
            errores.append("Formato de email inválido.\n");
        }
        if (!pwdFormato) {
            errores.append("Contraseña inválida, debe ser mayor a 6 caracteres.\n");
        }
        if (!telefonoFormato) {
            errores.append("Teléfono inválido, debe ingresar numeros de entre 7 a 15 digitos.\n");
        }
        if(!emailUnico){
            errores.append("El email ya está registrado.\n");
        }
        return errores.toString();
    }
}
