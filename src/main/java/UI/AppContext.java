/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import BaseDatos.*;

/**
 *
 * @author LAB-USR-LCENTRO
 */
public class AppContext {
    
    public static void main(String[] args) {
        IUsuarioDAO clienteDAO = new UsuarioSQliteDAO();
        
        LoginFrame login = new LoginFrame(clienteDAO);
        login.setVisible(true);
        System.out.println("Hola");
        
    }
    
    
}
