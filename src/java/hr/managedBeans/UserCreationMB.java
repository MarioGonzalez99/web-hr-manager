/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

import hr.jpa.controller.UsuarioJpaController;
import hr.jpa.entity.Usuario;
import hr.model.ControllerUtilities;
import hr.model.Decrypt;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Mario
 */
@Named(value = "userCreationMB")
@ViewScoped
public class UserCreationMB implements Serializable{
    
    
    private Usuario createdUser;
    private UsuarioJpaController userController;
    
    private String username;
    private String password;
    private String rol;
    
    /**
     * Creates a new instance of User
     */
    
    public UserCreationMB() {
    }

    @PostConstruct
    public void init(){
        userController = new UsuarioJpaController();
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public void createUser(){
        try {
            password = Decrypt.encryptText(password);
            createdUser = new Usuario(username, password, rol);
            userController.create(createdUser);
            ControllerUtilities.sendMessageInfo("Usuario Ingresado", "Se ha ingresado al usuario correctamente dentro de la base de datos");
        } catch (Exception ex) {
            Logger.getLogger(UserCreationMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
