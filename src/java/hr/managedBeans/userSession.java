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

/**
 *
 * @author Mario
 */
@Named(value = "userSession")
@SessionScoped
public class userSession implements Serializable {
    User user;
    UsuarioJpaController userController;
    /**
     * Creates a new instance of userSession
     */
    public userSession(){

    }
    
    @PostConstruct
    public void init() {
        user = new User();
        userController = new UsuarioJpaController();
        
    }
    
    public void validation(){
        try{
            Usuario usr = userController.findUsuario("mgonzalez");
            if(usr.getUsrUser().equals(user.getUsername())&&usr.getUsrPassword().equals(user.getPassword())){
                ControllerUtilities.redirect("store");
            }else{
                ControllerUtilities.sendMessageWarn("Invalid user", "Invalid user or password");
            }
        }catch(NullPointerException ex){
            ControllerUtilities.sendMessageWarn("Invalid user", "Invalid user or password");
            ControllerUtilities.redirect("index");
        }
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
