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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Mario
 */
@Named(value = "userUpdateMB")
@ViewScoped
public class UserUpdateMB implements Serializable {

    private UsuarioJpaController userController;

    private List<Usuario> users;
    private List<Usuario> filteredUsers;

    /**
     * Creates a new instance of User
     */
    public UserUpdateMB() {
    }

    @PostConstruct
    public void init() {
        userController = new UsuarioJpaController();
        users = userController.findUsuarioEntities();
    }

    public List<Usuario> getUsers() {
        return users;
    }

    public void setUsers(List<Usuario> users) {
        this.users = users;
    }

    public List<Usuario> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<Usuario> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public void onRowEdit(Usuario user) {
        try {
            user.setUsrPassword(Decrypt.encryptText(user.getUsrPassword()));
            userController.edit(user);
            ControllerUtilities.sendMessageInfo("Usuario Actualizado", "Se ha actualizado al usuario correctamente dentro de la base de datos");
        } catch (Exception ex) {
            Logger.getLogger(UserCreationMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowCancel() {
        ControllerUtilities.sendMessageInfo("Actualizacion cancelada", "Se ha cancelado la actualizacion del empleado");
    }
}
