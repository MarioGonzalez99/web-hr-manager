/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

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
    private final String USERNAME = "luis";
    private final String PASSWORD = "bitlab2020";
    
    /**
     * Creates a new instance of userSession
     */
    public userSession(){
        
    }
    
    @PostConstruct
    public void init() {
        user = new User();
    }
    
    public void validation(){
        if(USERNAME.equals(user.getUsername())&&PASSWORD.equals(user.getPassword())){
            ControllerUtilities.redirect("store");
        }else{
            ControllerUtilities.sendMessageWarn("Invalid user", "Invalid user or password");
        }
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
