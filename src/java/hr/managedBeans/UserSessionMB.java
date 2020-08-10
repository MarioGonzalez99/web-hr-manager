/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

import hr.jpa.controller.UsuarioJpaController;
import hr.jpa.entity.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import hr.model.ControllerUtilities;
import hr.model.Decrypt;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;

/**
 *
 * @author Mario
 */
@Named(value = "userSessionMB")
@SessionScoped
public class UserSessionMB implements Serializable {

    UserMB user;
    UsuarioJpaController userController;

    /**
     * Creates a new instance of userSession
     */
    public UserSessionMB() {

    }

    @PostConstruct
    public void init() {
        user = new UserMB();
        userController = new UsuarioJpaController();
    }

    public void userValidation() {
        boolean isNotValidUser = true;
        try {
            Usuario usr = userController.findUsuario(user.getUsername());
            if (usr.getUsrUser() != null) {
                if (Decrypt.decryptText(usr.getUsrPassword()).equals(user.getPassword())) {
                    user.setRol(usr.getUsrRol());
                    if(user.getRol().toUpperCase().startsWith("A")){
                        ControllerUtilities.redirect("indexA");
                    }else{
                        ControllerUtilities.redirect("index");
                    }
                    isNotValidUser = false;
                }
            }
            if (isNotValidUser) {
                ControllerUtilities.sendMessageWarn("Invalid user", "Invalid user or password");
            }
        } catch (NullPointerException ex) {
            ControllerUtilities.redirect("login");
        }

    }

    public void sessionValidation() {
        if (user.getRol() == null) {
            ControllerUtilities.redirect("login");

        }
    }
    
    public void sessionValidationA() {
        if (user.getRol() == null || !user.getRol().toUpperCase().startsWith("A")) {
            ControllerUtilities.redirect("login");
        } 
    }
    
    @PreDestroy
    public void destruct() {
        userController.getEntityManagerFactory().close();
    }

    public UserMB getUser() {
        return user;
    }

    public void setUser(UserMB user) {
        this.user = user;
    }

    public void closeSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ControllerUtilities.redirect("login");
    }
}
