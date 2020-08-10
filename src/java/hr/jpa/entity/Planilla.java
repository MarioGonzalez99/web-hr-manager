/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Mario
 */
@Entity
@Table(name = "REG_PLAN_PLANILLA")
@NamedQueries({
    @NamedQuery(name = "Planilla.findAll", query = "SELECT p FROM Planilla p"),
    @NamedQuery(name = "Planilla.findByEmpId", query = "SELECT p FROM Planilla p WHERE p.planillaPK.empId = :empId"),
    @NamedQuery(name = "Planilla.findByPlanFechaPlanilla", query = "SELECT p FROM Planilla p WHERE p.planillaPK.planFechaPlanilla = :planFechaPlanilla"),
    @NamedQuery(name = "Planilla.findByPlanSalarioBase", query = "SELECT p FROM Planilla p WHERE p.planSalarioBase = :planSalarioBase"),
    @NamedQuery(name = "Planilla.findByPlanMontoDescuentoAfp", query = "SELECT p FROM Planilla p WHERE p.planMontoDescuentoAfp = :planMontoDescuentoAfp"),
    @NamedQuery(name = "Planilla.findByPlanMontoDescuentoIsss", query = "SELECT p FROM Planilla p WHERE p.planMontoDescuentoIsss = :planMontoDescuentoIsss"),
    @NamedQuery(name = "Planilla.findByPlanMontoDescuentoRenta", query = "SELECT p FROM Planilla p WHERE p.planMontoDescuentoRenta = :planMontoDescuentoRenta"),
    @NamedQuery(name = "Planilla.findByPlanSalarioNeto", query = "SELECT p FROM Planilla p WHERE p.planSalarioNeto = :planSalarioNeto"),
    @NamedQuery(name = "Planilla.findByAFechaCrea", query = "SELECT p FROM Planilla p WHERE p.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "Planilla.findByAUsuarioCrea", query = "SELECT p FROM Planilla p WHERE p.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "Planilla.findByAFechaModificacion", query = "SELECT p FROM Planilla p WHERE p.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "Planilla.findByAUsuarioModifica", query = "SELECT p FROM Planilla p WHERE p.aUsuarioModifica = :aUsuarioModifica")})
public class Planilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlanillaPK planillaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PLAN_SALARIO_BASE")
    private BigDecimal planSalarioBase;
    @Column(name = "PLAN_MONTO_DESCUENTO_AFP")
    private BigDecimal planMontoDescuentoAfp;
    @Column(name = "PLAN_MONTO_DESCUENTO_ISSS")
    private BigDecimal planMontoDescuentoIsss;
    @Column(name = "PLAN_MONTO_DESCUENTO_RENTA")
    private BigDecimal planMontoDescuentoRenta;
    @Column(name = "PLAN_SALARIO_NETO")
    private BigDecimal planSalarioNeto;
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
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;

    public Planilla() {
    }

    public Planilla(PlanillaPK planillaPK) {
        this.planillaPK = planillaPK;
    }

    public Planilla(int empId, Date planFechaPlanilla) {
        this.planillaPK = new PlanillaPK(empId, planFechaPlanilla);
    }
    
    public Planilla(int empId, Date planFechaPlanilla, BigDecimal planSalarioBase) {
        this.planillaPK = new PlanillaPK(empId, planFechaPlanilla);
        this.empleado = new Empleado(empId);
        this.planSalarioBase = planSalarioBase;
        BigDecimal afpTax = new BigDecimal("0.0725");
        this.planMontoDescuentoAfp = this.planSalarioBase.multiply(afpTax);
        BigDecimal isssTax = new BigDecimal("0.03");
        this.planMontoDescuentoIsss = this.planSalarioBase.multiply(isssTax);
        BigDecimal intermediateSalary = this.planSalarioBase.subtract(this.planMontoDescuentoAfp).subtract(this.planMontoDescuentoIsss);
        if(this.planSalarioBase.compareTo(new BigDecimal("472.01"))<0){
            this.planMontoDescuentoRenta = new BigDecimal("0");
        }else if(this.planSalarioBase.compareTo(new BigDecimal("472.01"))>=0 && this.planSalarioBase.compareTo(new BigDecimal("895.25"))<0){
            this.planMontoDescuentoRenta = intermediateSalary.multiply(new BigDecimal("0.1"));
        }else if(this.planSalarioBase.compareTo(new BigDecimal("895.251"))>=0 && this.planSalarioBase.compareTo(new BigDecimal("2038.11"))<0){
            this.planMontoDescuentoRenta = intermediateSalary.multiply(new BigDecimal("0.2"));
        }else{
            this.planMontoDescuentoRenta = intermediateSalary.multiply(new BigDecimal("0.3"));
        }
        this.planSalarioNeto = this.planSalarioBase.subtract(this.planMontoDescuentoAfp).subtract(this.planMontoDescuentoIsss).subtract(this.planMontoDescuentoRenta);
    }

    public PlanillaPK getPlanillaPK() {
        return planillaPK;
    }

    public void setPlanillaPK(PlanillaPK planillaPK) {
        this.planillaPK = planillaPK;
    }

    public BigDecimal getPlanSalarioBase() {
        return planSalarioBase;
    }

    public void setPlanSalarioBase(BigDecimal planSalarioBase) {
        this.planSalarioBase = planSalarioBase;
    }

    public BigDecimal getPlanMontoDescuentoAfp() {
        return planMontoDescuentoAfp;
    }

    public void setPlanMontoDescuentoAfp(BigDecimal planMontoDescuentoAfp) {
        this.planMontoDescuentoAfp = planMontoDescuentoAfp;
    }

    public BigDecimal getPlanMontoDescuentoIsss() {
        return planMontoDescuentoIsss;
    }

    public void setPlanMontoDescuentoIsss(BigDecimal planMontoDescuentoIsss) {
        this.planMontoDescuentoIsss = planMontoDescuentoIsss;
    }

    public BigDecimal getPlanMontoDescuentoRenta() {
        return planMontoDescuentoRenta;
    }

    public void setPlanMontoDescuentoRenta(BigDecimal planMontoDescuentoRenta) {
        this.planMontoDescuentoRenta = planMontoDescuentoRenta;
    }

    public BigDecimal getPlanSalarioNeto() {
        return planSalarioNeto;
    }

    public void setPlanSalarioNeto(BigDecimal planSalarioNeto) {
        this.planSalarioNeto = planSalarioNeto;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planillaPK != null ? planillaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planilla)) {
            return false;
        }
        Planilla other = (Planilla) object;
        if ((this.planillaPK == null && other.planillaPK != null) || (this.planillaPK != null && !this.planillaPK.equals(other.planillaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hr.jpa.entity.Planilla[ planillaPK=" + planillaPK + " ]";
    }
    
}
