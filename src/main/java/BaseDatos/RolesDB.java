package BaseDatos;

public enum RolesDB {
    ADMIN("ADMIN"),
    CLIENTE("CLIENTE");
    private final String rol;
    private RolesDB(String rol){
        this.rol=rol;
    }
    public String getRol() {
        return rol;
    }
}
