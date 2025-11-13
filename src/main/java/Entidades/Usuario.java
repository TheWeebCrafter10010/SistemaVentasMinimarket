
package Entidades;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String pwd;//Contraseña
    private String telefono;
    private String direccion;

    public Usuario(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.email = builder.email;
        this.pwd = builder.pwd;
        this.telefono = builder.telefono;
        this.direccion = builder.direccion;
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", pwd=" + pwd + ", telefono=" + telefono + ", direccion=" + direccion + '}';
    }
    
    public static class Builder{
        private int id;
        private String nombre;
        private String apellido;
        private String email;
        private String pwd;//Contraseña
        private String telefono;
        private String direccion;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }
        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public Builder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public Builder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        public Builder setDireccion(String direccion) {
            this.direccion = direccion;
            return this;
        }
        public Admin buildAdmin(){
            
            return new Admin(this);
        }
        
        public Cliente buildCliente(){
            return new Cliente(this);
        }
    
    
    }
}
