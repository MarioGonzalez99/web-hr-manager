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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "REG_EMP_EMPLEADO")
@NamedQueries({
    @NamedQuery(name = "RegEmpEmpleado.findAll", query = "SELECT r FROM RegEmpEmpleado r"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpId", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empId = :empId"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpNombre", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empNombre = :empNombre"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpApellido", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empApellido = :empApellido"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpGenero", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empGenero = :empGenero"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpCorreo", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empCorreo = :empCorreo"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpFechaNacimiento", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empFechaNacimiento = :empFechaNacimiento"),
    @NamedQuery(name = "RegEmpEmpleado.findByEmpFechaContratacion", query = "SELECT r FROM RegEmpEmpleado r WHERE r.empFechaContratacion = :empFechaContratacion"),
    @NamedQuery(name = "RegEmpEmpleado.findByAFechaCrea", query = "SELECT r FROM RegEmpEmpleado r WHERE r.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "RegEmpEmpleado.findByAUsuarioCrea", query = "SELECT r FROM RegEmpEmpleado r WHERE r.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "RegEmpEmpleado.findByAFechaModificacion", query = "SELECT r FROM RegEmpEmpleado r WHERE r.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "RegEmpEmpleado.findByAUsuarioModifica", query = "SELECT r FROM RegEmpEmpleado r WHERE r.aUsuarioModifica = :aUsuarioModifica")})
public class RegEmpEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_ID", nullable = false)
    private Integer empId;
    @Size(max = 100)
    @Column(name = "EMP_NOMBRE", length = 100)
    private String empNombre;
    @Size(max = 100)
    @Column(name = "EMP_APELLIDO", length = 100)
    private String empApellido;
    @Size(max = 1)
    @Column(name = "EMP_GENERO", length = 1)
    private String empGenero;
    @Size(max = 100)
    @Column(name = "EMP_CORREO", length = 100)
    private String empCorreo;
    @Column(name = "EMP_FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date empFechaNacimiento;
    @Column(name = "EMP_FECHA_CONTRATACION")
    @Temporal(TemporalType.DATE)
    private Date empFechaContratacion;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regEmpEmpleado", fetch = FetchType.LAZY)
    private List<RegPlanPlanilla> regPlanPlanillaList;
    @JoinColumn(name = "DEP_NOMBRE", referencedColumnName = "DEP_NOMBRE")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegDepDepartamento depNombre;
    @OneToMany(mappedBy = "empIdJefe", fetch = FetchType.LAZY)
    private List<RegEmpEmpleado> regEmpEmpleadoList;
    @JoinColumn(name = "EMP_ID_JEFE", referencedColumnName = "EMP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegEmpEmpleado empIdJefe;
    @JoinColumn(name = "EST_ID", referencedColumnName = "EST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegEstEstadoEmpleado estId;

    public RegEmpEmpleado() {
    }

    public RegEmpEmpleado(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpApellido() {
        return empApellido;
    }

    public void setEmpApellido(String empApellido) {
        this.empApellido = empApellido;
    }

    public String getEmpGenero() {
        return empGenero;
    }

    public void setEmpGenero(String empGenero) {
        this.empGenero = empGenero;
    }

    public String getEmpCorreo() {
        return empCorreo;
    }

    public void setEmpCorreo(String empCorreo) {
        this.empCorreo = empCorreo;
    }

    public Date getEmpFechaNacimiento() {
        return empFechaNacimiento;
    }

    public void setEmpFechaNacimiento(Date empFechaNacimiento) {
        this.empFechaNacimiento = empFechaNacimiento;
    }

    public Date getEmpFechaContratacion() {
        return empFechaContratacion;
    }

    public void setEmpFechaContratacion(Date empFechaContratacion) {
        this.empFechaContratacion = empFechaContratacion;
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

    public List<RegPlanPlanilla> getRegPlanPlanillaList() {
        return regPlanPlanillaList;
    }

    public void setRegPlanPlanillaList(List<RegPlanPlanilla> regPlanPlanillaList) {
        this.regPlanPlanillaList = regPlanPlanillaList;
    }

    public RegDepDepartamento getDepNombre() {
        return depNombre;
    }

    public void setDepNombre(RegDepDepartamento depNombre) {
        this.depNombre = depNombre;
    }

    public List<RegEmpEmpleado> getRegEmpEmpleadoList() {
        return regEmpEmpleadoList;
    }

    public void setRegEmpEmpleadoList(List<RegEmpEmpleado> regEmpEmpleadoList) {
        this.regEmpEmpleadoList = regEmpEmpleadoList;
    }

    public RegEmpEmpleado getEmpIdJefe() {
        return empIdJefe;
    }

    public void setEmpIdJefe(RegEmpEmpleado empIdJefe) {
        this.empIdJefe = empIdJefe;
    }

    public RegEstEstadoEmpleado getEstId() {
        return estId;
    }

    public void setEstId(RegEstEstadoEmpleado estId) {
        this.estId = estId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegEmpEmpleado)) {
            return false;
        }
        RegEmpEmpleado other = (RegEmpEmpleado) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.echo.bitlab.jpa.RegEmpEmpleado[ empId=" + empId + " ]";
    }
    
}
