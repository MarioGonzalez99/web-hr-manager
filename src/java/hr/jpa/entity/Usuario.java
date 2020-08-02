/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Mario
 */
@Entity
@Table(name = "USR_USUARIO")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsrUser", query = "SELECT u FROM Usuario u WHERE u.usrUser = :usrUser"),
    @NamedQuery(name = "Usuario.findByUsrPassword", query = "SELECT u FROM Usuario u WHERE u.usrPassword = :usrPassword"),
    @NamedQuery(name = "Usuario.findByUsrRol", query = "SELECT u FROM Usuario u WHERE u.usrRol = :usrRol")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USR_USER")
    private String usrUser;
    @Basic(optional = false)
    @Column(name = "USR_PASSWORD")
    private String usrPassword;
    @Basic(optional = false)
    @Column(name = "USR_ROL")
    private String usrRol;

    public Usuario() {
    }

    public Usuario(String usrUser) {
        this.usrUser = usrUser;
    }

    public Usuario(String usrUser, String usrPassword, String usrRol) {
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usrUser == null && other.usrUser != null) || (this.usrUser != null && !this.usrUser.equals(other.usrUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.jpa.entity.Usuario[ usrUser=" + usrUser + " ]";
    }
    
}
