/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Mario
 */
@Entity
@Table(name = "REG_EST_ESTADO_EMPLEADO")
@NamedQueries({
    @NamedQuery(name = "RegEstEstadoEmpleado.findAll", query = "SELECT r FROM RegEstEstadoEmpleado r"),
    @NamedQuery(name = "RegEstEstadoEmpleado.findByEstId", query = "SELECT r FROM RegEstEstadoEmpleado r WHERE r.estId = :estId"),
    @NamedQuery(name = "RegEstEstadoEmpleado.findByNombre", query = "SELECT r FROM RegEstEstadoEmpleado r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "RegEstEstadoEmpleado.findByAFechaCrea", query = "SELECT r FROM RegEstEstadoEmpleado r WHERE r.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "RegEstEstadoEmpleado.findByAUsuarioCrea", query = "SELECT r FROM RegEstEstadoEmpleado r WHERE r.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "RegEstEstadoEmpleado.findByAFechaModificacion", query = "SELECT r FROM RegEstEstadoEmpleado r WHERE r.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "RegEstEstadoEmpleado.findByAUsuarioModifica", query = "SELECT r FROM RegEstEstadoEmpleado r WHERE r.aUsuarioModifica = :aUsuarioModifica")})
public class RegEstEstadoEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "EST_ID", nullable = false, length = 1)
    private String estId;
    @Size(max = 100)
    @Column(length = 100)
    private String nombre;
    @Column(name = "A_FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date aFechaCrea;
    @Size(max = 10)
    @Column(name = "A_USUARIO_CREA", length = 10)
    private String aUsuarioCrea;
    @Column(name = "A_FECHA_MODIFICACION")
    @Temporal(TemporalType.DATE)
    private Date aFechaModificacion;
    @Size(max = 10)
    @Column(name = "A_USUARIO_MODIFICA", length = 10)
    private String aUsuarioModifica;
    @OneToMany(mappedBy = "estId", fetch = FetchType.LAZY)
    private List<RegEmpEmpleado> regEmpEmpleadoList;

    public RegEstEstadoEmpleado() {
    }

    public RegEstEstadoEmpleado(String estId) {
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

    public List<RegEmpEmpleado> getRegEmpEmpleadoList() {
        return regEmpEmpleadoList;
    }

    public void setRegEmpEmpleadoList(List<RegEmpEmpleado> regEmpEmpleadoList) {
        this.regEmpEmpleadoList = regEmpEmpleadoList;
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
        if (!(object instanceof RegEstEstadoEmpleado)) {
            return false;
        }
        RegEstEstadoEmpleado other = (RegEstEstadoEmpleado) object;
        if ((this.estId == null && other.estId != null) || (this.estId != null && !this.estId.equals(other.estId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.echo.bitlab.jpa.RegEstEstadoEmpleado[ estId=" + estId + " ]";
    }
    
}
