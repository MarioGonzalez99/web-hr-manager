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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "REG_EMP_EMPLEADO")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByEmpId", query = "SELECT e FROM Empleado e WHERE e.empId = :empId"),
    @NamedQuery(name = "Empleado.findByEmpNombre", query = "SELECT e FROM Empleado e WHERE e.empNombre = :empNombre"),
    @NamedQuery(name = "Empleado.findByEmpApellido", query = "SELECT e FROM Empleado e WHERE e.empApellido = :empApellido"),
    @NamedQuery(name = "Empleado.findByEmpGenero", query = "SELECT e FROM Empleado e WHERE e.empGenero = :empGenero"),
    @NamedQuery(name = "Empleado.findByEmpCorreo", query = "SELECT e FROM Empleado e WHERE e.empCorreo = :empCorreo"),
    @NamedQuery(name = "Empleado.findByEmpFechaNacimiento", query = "SELECT e FROM Empleado e WHERE e.empFechaNacimiento = :empFechaNacimiento"),
    @NamedQuery(name = "Empleado.findByEmpFechaContratacion", query = "SELECT e FROM Empleado e WHERE e.empFechaContratacion = :empFechaContratacion"),
    @NamedQuery(name = "Empleado.findByAFechaCrea", query = "SELECT e FROM Empleado e WHERE e.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "Empleado.findByAUsuarioCrea", query = "SELECT e FROM Empleado e WHERE e.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "Empleado.findByAFechaModificacion", query = "SELECT e FROM Empleado e WHERE e.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "Empleado.findByAUsuarioModifica", query = "SELECT e FROM Empleado e WHERE e.aUsuarioModifica = :aUsuarioModifica")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    private Integer empId;
    @Column(name = "EMP_NOMBRE")
    private String empNombre;
    @Column(name = "EMP_APELLIDO")
    private String empApellido;
    @Column(name = "EMP_GENERO")
    private String empGenero;
    @Column(name = "EMP_CORREO")
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
    @Column(name = "A_USUARIO_CREA")
    private String aUsuarioCrea;
    @Column(name = "A_FECHA_MODIFICACION")
    @Temporal(TemporalType.DATE)
    private Date aFechaModificacion;
    @Column(name = "A_USUARIO_MODIFICA")
    private String aUsuarioModifica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empleado")
    private List<Planilla> planillaList;
    @JoinColumn(name = "DEP_NOMBRE", referencedColumnName = "DEP_NOMBRE")
    @ManyToOne
    private Departamento depNombre;
    @OneToMany(mappedBy = "empIdJefe")
    private List<Empleado> empleadoList;
    @JoinColumn(name = "EMP_ID_JEFE", referencedColumnName = "EMP_ID")
    @ManyToOne
    private Empleado empIdJefe;
    @JoinColumn(name = "EST_ID", referencedColumnName = "EST_ID")
    @ManyToOne
    private EstadoEmpleado estId;

    public Empleado() {
    }

    public Empleado(Integer empId) {
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

    public List<Planilla> getPlanillaList() {
        return planillaList;
    }

    public void setPlanillaList(List<Planilla> planillaList) {
        this.planillaList = planillaList;
    }

    public Departamento getDepNombre() {
        return depNombre;
    }

    public void setDepNombre(Departamento depNombre) {
        this.depNombre = depNombre;
    }

    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    public Empleado getEmpIdJefe() {
        return empIdJefe;
    }

    public void setEmpIdJefe(Empleado empIdJefe) {
        this.empIdJefe = empIdJefe;
    }

    public EstadoEmpleado getEstId() {
        return estId;
    }

    public void setEstId(EstadoEmpleado estId) {
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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.jpa.entity.Empleado[ empId=" + empId + " ]";
    }
    
}
