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
@Table(name = "REG_EST_ESTADO_EMPLEADO")
@NamedQueries({
    @NamedQuery(name = "EstadoEmpleado.findAll", query = "SELECT e FROM EstadoEmpleado e"),
    @NamedQuery(name = "EstadoEmpleado.findByEstId", query = "SELECT e FROM EstadoEmpleado e WHERE e.estId = :estId"),
    @NamedQuery(name = "EstadoEmpleado.findByNombre", query = "SELECT e FROM EstadoEmpleado e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EstadoEmpleado.findByAFechaCrea", query = "SELECT e FROM EstadoEmpleado e WHERE e.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "EstadoEmpleado.findByAUsuarioCrea", query = "SELECT e FROM EstadoEmpleado e WHERE e.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "EstadoEmpleado.findByAFechaModificacion", query = "SELECT e FROM EstadoEmpleado e WHERE e.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "EstadoEmpleado.findByAUsuarioModifica", query = "SELECT e FROM EstadoEmpleado e WHERE e.aUsuarioModifica = :aUsuarioModifica")})
public class EstadoEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EST_ID")
    private String estId;
    @Column(name = "NOMBRE")
    private String nombre;
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
    @OneToMany(mappedBy = "estId")
    private List<Empleado> empleadoList;

    public EstadoEmpleado() {
    }

    public EstadoEmpleado(String estId) {
        this.estId = estId;
    }

    public String getEstId() {
        return estId;
    }

    public void setEstId(String estId) {
        this.estId = estId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (estId != null ? estId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoEmpleado)) {
            return false;
        }
        EstadoEmpleado other = (EstadoEmpleado) object;
        if ((this.estId == null && other.estId != null) || (this.estId != null && !this.estId.equals(other.estId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.jpa.entity.EstadoEmpleado[ estId=" + estId + " ]";
    }
    
}
