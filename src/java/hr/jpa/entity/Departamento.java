/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Mario
 */
@Entity
@Table(name = "REG_DEP_DEPARTAMENTO")
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
    @NamedQuery(name = "Departamento.findByDepNombre", query = "SELECT d FROM Departamento d WHERE d.depNombre = :depNombre"),
    @NamedQuery(name = "Departamento.findByAFechaCrea", query = "SELECT d FROM Departamento d WHERE d.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "Departamento.findByAUsuarioCrea", query = "SELECT d FROM Departamento d WHERE d.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "Departamento.findByAFechaModificacion", query = "SELECT d FROM Departamento d WHERE d.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "Departamento.findByAUsuarioModifica", query = "SELECT d FROM Departamento d WHERE d.aUsuarioModifica = :aUsuarioModifica")})
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DEP_NOMBRE")
    private String depNombre;
    @Column(name = "A_FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date aFechaCrea;
    @Column(name = "A_USUARIO_CREA")
    private String aUsuarioCrea;
    @Column(name = "A_FECHA_MODIFICACION")
    @Temporal(TemporalType.DATE)
    private Date aFechaModificacion;
    @Column(name = "A_USUARIO_MODIFICA")
    private String aUsuarioModifica;
    @OneToMany(mappedBy = "depNombre")
    private List<Empleado> empleadoList;

    public Departamento() {
    }

    public Departamento(String depNombre) {
        this.depNombre = depNombre;
    }

    public String getDepNombre() {
        return depNombre;
    }

    public void setDepNombre(String depNombre) {
        this.depNombre = depNombre;
    }

    public Date getAFechaCrea() {
        return aFechaCrea;
    }

    public void setAFechaCrea(Date aFechaCrea) {
        this.aFechaCrea = aFechaCrea;
    }

    public String getAUsuarioCrea() {
        return aUsuarioCrea;
    }

    public void setAUsuarioCrea(String aUsuarioCrea) {
        this.aUsuarioCrea = aUsuarioCrea;
    }

    public Date getAFechaModificacion() {
        return aFechaModificacion;
    }

    public void setAFechaModificacion(Date aFechaModificacion) {
        this.aFechaModificacion = aFechaModificacion;
    }

    public String getAUsuarioModifica() {
        return aUsuarioModifica;
    }

    public void setAUsuarioModifica(String aUsuarioModifica) {
        this.aUsuarioModifica = aUsuarioModifica;
    }

    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depNombre != null ? depNombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.depNombre == null && other.depNombre != null) || (this.depNombre != null && !this.depNombre.equals(other.depNombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.jpa.entity.Departamento[ depNombre=" + depNombre + " ]";
    }
    
}
