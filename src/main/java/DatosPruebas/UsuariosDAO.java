package DatosPruebas;

import BaseDatos.IUsuarioDAO;
import Entidades.Admin;
import Entidades.Cliente;
import Entidades.Usuario;
import Observadores.DaoObserver;
import Observadores.DaoSubject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuariosDAO implements IUsuarioDAO, DaoSubject {

    private List<DaoObserver> observers;
    private HashMap<String, Usuario> usuarios;
    private int contadorIDs = 1;
    public UsuariosDAO() {
        observers = new ArrayList<>();
        this.usuarios = new HashMap<>();
        Admin admin1 = new Usuario.Builder()
                .setId(contadorIDs)
                .setNombre("Alonso")
                .setApellido("Gomez")
                .setEmail("alonso@gmail.com")
                .setPwd("soyadmin123")
                .setTelefono("999111222")
                .setDireccion("Calle A 123")
                .buildAdmin();
        contadorIDs++;
        Admin admin2 = new Usuario.Builder()
                .setId(contadorIDs)
                .setNombre("Sebastian")
                .setApellido("Lopez")
                .setEmail("sebastian@admin.com")
                .setPwd("soyadmin456")
                .setTelefono("999222333")
                .setDireccion("Calle B 456")
                .buildAdmin();
        contadorIDs++;
        // Cliente 1
        Cliente cliente1 = new Usuario.Builder()
                .setId(contadorIDs)
                .setNombre("Carlos")
                .setApellido("Pérez")
                .setEmail("carlos@cliente.com")
                .setPwd("carlos1111")
                .setTelefono("988111222")
                .setDireccion("Av. Lima 101")
                .buildCliente();
        contadorIDs++;

        // Cliente 2
        Cliente cliente2 = new Usuario.Builder()
                .setId(contadorIDs)
                .setNombre("Ana")
                .setApellido("Torres")
                .setEmail("ana@cliente.com")
                .setPwd("ana2222")
                .setTelefono("988222333")
                .setDireccion("Av. Arequipa 202")
                .buildCliente();
        contadorIDs++;

        usuarios.put(admin1.getEmail(), admin1);
        usuarios.put(admin2.getEmail(), admin2);
        usuarios.put(cliente1.getEmail(), cliente1);
        usuarios.put(cliente2.getEmail(), cliente2);

    }

    @Override
    public Usuario obtenerUsuarioLogin(String email, String pwd) {

        if (!usuarios.containsKey(email)) {
            return null;
        }
        Usuario user = usuarios.get(email);

        if(user.getPwd().equals(pwd)){
            return user;
        }
        return null;

    }

    @Override
    public boolean emailYaRegistrado(String email) {
        return usuarios.containsKey(email);
    }

    @Override
    public boolean insertarCliente(Cliente cliente) {
        cliente.setId(contadorIDs);
        usuarios.put(cliente.getEmail(), cliente);
        contadorIDs++;
        String notificacion = "Nuevo cliente registrado - " + cliente.getId()+" | " + cliente.getEmail();
        notifyObservers(notificacion);
        return usuarios.containsKey(cliente.getEmail());
    }

    @Override
    public Map<String,Usuario> obtenerClientes() {
        return usuarios;
    }

    @Override
    public void attach(DaoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(DaoObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String mensaje) {
        for( DaoObserver o : observers){
            o.notificarCambio(mensaje);
        }
    }
}
