package DataTransferObjects;

public class ValidacionRegistro {
    public boolean emailFormato;
    public boolean pwdFormato;
    public boolean telefonoFormato;

    public boolean esValido() {
        return emailFormato && pwdFormato && telefonoFormato;
    }

    public String mostrarErrores() {
        StringBuilder errores = new StringBuilder();
        if (!emailFormato) {
            errores.append("Formato de email inválido.\n");
        }
        if (!pwdFormato) {
            errores.append("Formato de contraseña inválido.\n");
        }
        if (!telefonoFormato) {
            errores.append("Formato de teléfono inválido.\n");
        }
        return errores.toString();
    }
}
