/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.model.utilities;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import hr.managedBeans.userSession;

/**
 *
 * @author Mario
 */
public class ControllerUtilities {
    public static void sendMessage(FacesMessage.Severity severity, String header, String detail){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, header, detail));
    }
    public static void sendMessageInfo(String header, String detail){
        sendMessage(FacesMessage.SEVERITY_INFO, header, detail);
    }
    public static void sendMessageWarn(String header, String detail){
        sendMessage(FacesMessage.SEVERITY_WARN, header, detail);
    }
    public static void sendMessageError(String header, String detail){
        sendMessage(FacesMessage.SEVERITY_ERROR, header, detail);
    }
    public static void redirect(String page){
        try {
            ExternalContext context  = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath()+"/"+page+".echo");
        } catch (IOException ex) {
            Logger.getLogger(userSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
