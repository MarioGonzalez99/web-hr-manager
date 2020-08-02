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
@Table(name = "REG_DEP_DEPARTAMENTO")
@NamedQueries({
    @NamedQuery(name = "RegDepDepartamento.findAll", query = "SELECT r FROM RegDepDepartamento r"),
    @NamedQuery(name = "RegDepDepartamento.findByDepNombre", query = "SELECT r FROM RegDepDepartamento r WHERE r.depNombre = :depNombre"),
    @NamedQuery(name = "RegDepDepartamento.findByAFechaCrea", query = "SELECT r FROM RegDepDepartamento r WHERE r.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "RegDepDepartamento.findByAUsuarioCrea", query = "SELECT r FROM RegDepDepartamento r WHERE r.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "RegDepDepartamento.findByAFechaModificacion", query = "SELECT r FROM RegDepDepartamento r WHERE r.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "RegDepDepartamento.findByAUsuarioModifica", query = "SELECT r FROM RegDepDepartamento r WHERE r.aUsuarioModifica = :aUsuarioModifica")})
public class RegDepDepartamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DEP_NOMBRE", nullable = false, length = 100)
    private String depNombre;
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
    @OneToMany(mappedBy = "depNombre", fetch = FetchType.LAZY)
    private List<RegEmpEmpleado> regEmpEmpleadoList;

    public RegDepDepartamento() {
    }

    public RegDepDepartamento(String depNombre) {
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

    public List<RegEmpEmpleado> getRegEmpEmpleadoList() {
        return regEmpEmpleadoList;
    }

    public void setRegEmpEmpleadoList(List<RegEmpEmpleado> regEmpEmpleadoList) {
        this.regEmpEmpleadoList = regEmpEmpleadoList;
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
        if (!(object instanceof RegDepDepartamento)) {
            return false;
        }
        RegDepDepartamento other = (RegDepDepartamento) object;
        if ((this.depNombre == null && other.depNombre != null) || (this.depNombre != null && !this.depNombre.equals(other.depNombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.echo.bitlab.jpa.RegDepDepartamento[ depNombre=" + depNombre + " ]";
    }
    
}
