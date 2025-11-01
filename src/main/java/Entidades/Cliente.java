package Entidades;

import java.util.List;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String pwd;//Contraseña
    private String telefono;
    private String direccion;
    private List<Venta> compras;

    public Cliente(){
        //Constructor vacio para crear un cliente nuevo
    }

    public Cliente(Builder builder){
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.email = builder.email;
        this.pwd = builder.pwd;
        this.telefono = builder.telefono;
        this.direccion = builder.direccion;
        this.compras = builder.compras;
    }

    public Cliente(int id, String nombre, String apellido, String email, String pwd, String telefono, List<Venta> compras) {
        //leer de la bd
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.pwd = pwd;
        this.telefono = telefono;
        this.compras = compras;
    }
    public Cliente(String nombre, String apellido, String email, String pwd, String telefono) {
        //Insertar en la base de datos
        //El id y las compras se generan automaticamente
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.pwd = pwd;
        this.telefono = telefono;

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Venta> getCompras() {
        return compras;
    }
    public void setId(int id) {//SOLO PARA PRUEBAS MOCK, DESPUES SE ELIMINARA
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public static class Builder{
        private int id;
        private String nombre;
        private String apellido;
        private String email;
        private String pwd;//Contraseña
        private String telefono;
        private String direccion;
        private List<Venta> compras;

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
        public Builder setCompras(List<Venta> compras) {
            this.compras = compras;
            return this;
        }
        public Cliente build(){
            return new Cliente(this);
        }
    }
}
