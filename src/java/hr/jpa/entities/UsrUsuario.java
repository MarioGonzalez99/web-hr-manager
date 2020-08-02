/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Mario
 */
@Entity
@Table(name = "USR_USUARIO")
@NamedQueries({
    @NamedQuery(name = "UsrUsuario.findAll", query = "SELECT u FROM UsrUsuario u"),
    @NamedQuery(name = "UsrUsuario.findByUsrUser", query = "SELECT u FROM UsrUsuario u WHERE u.usrUser = :usrUser"),
    @NamedQuery(name = "UsrUsuario.findByUsrPassword", query = "SELECT u FROM UsrUsuario u WHERE u.usrPassword = :usrPassword"),
    @NamedQuery(name = "UsrUsuario.findByUsrRol", query = "SELECT u FROM UsrUsuario u WHERE u.usrRol = :usrRol")})
public class UsrUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USR_USER", nullable = false, length = 10)
    private String usrUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "USR_PASSWORD", nullable = false, length = 1000)
    private String usrPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "USR_ROL", nullable = false, length = 2)
    private String usrRol;

    public UsrUsuario() {
    }

    public UsrUsuario(String usrUser) {
        this.usrUser = usrUser;
    }

    public UsrUsuario(String usrUser, String usrPassword, String usrRol) {
        this.usrUser = usrUser;
        this.usrPassword = usrPassword;
        this.usrRol = usrRol;
    }

    public String getUsrUser() {
        return usrUser;
    }

    public void setUsrUser(String usrUser) {
        this.usrUser = usrUser;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrRol() {
        return usrRol;
    }

    public void setUsrRol(String usrRol) {
        this.usrRol = usrRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrUser != null ? usrUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrUsuario)) {
            return false;
        }
        UsrUsuario other = (UsrUsuario) object;
        if ((this.usrUser == null && other.usrUser != null) || (this.usrUser != null && !this.usrUser.equals(other.usrUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.echo.bitlab.jpa.UsrUsuario[ usrUser=" + usrUser + " ]";
    }
    
}
