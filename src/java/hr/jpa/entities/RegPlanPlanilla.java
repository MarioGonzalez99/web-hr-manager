/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Mario
 */
@Entity
@Table(name = "REG_PLAN_PLANILLA")
@NamedQueries({
    @NamedQuery(name = "RegPlanPlanilla.findAll", query = "SELECT r FROM RegPlanPlanilla r"),
    @NamedQuery(name = "RegPlanPlanilla.findByEmpId", query = "SELECT r FROM RegPlanPlanilla r WHERE r.regPlanPlanillaPK.empId = :empId"),
    @NamedQuery(name = "RegPlanPlanilla.findByPlanFechaPlanilla", query = "SELECT r FROM RegPlanPlanilla r WHERE r.regPlanPlanillaPK.planFechaPlanilla = :planFechaPlanilla"),
    @NamedQuery(name = "RegPlanPlanilla.findByPlanSalarioBase", query = "SELECT r FROM RegPlanPlanilla r WHERE r.planSalarioBase = :planSalarioBase"),
    @NamedQuery(name = "RegPlanPlanilla.findByPlanMontoDescuentoAfp", query = "SELECT r FROM RegPlanPlanilla r WHERE r.planMontoDescuentoAfp = :planMontoDescuentoAfp"),
    @NamedQuery(name = "RegPlanPlanilla.findByPlanMontoDescuentoIsss", query = "SELECT r FROM RegPlanPlanilla r WHERE r.planMontoDescuentoIsss = :planMontoDescuentoIsss"),
    @NamedQuery(name = "RegPlanPlanilla.findByPlanMontoDescuentoRenta", query = "SELECT r FROM RegPlanPlanilla r WHERE r.planMontoDescuentoRenta = :planMontoDescuentoRenta"),
    @NamedQuery(name = "RegPlanPlanilla.findByPlanSalarioNeto", query = "SELECT r FROM RegPlanPlanilla r WHERE r.planSalarioNeto = :planSalarioNeto"),
    @NamedQuery(name = "RegPlanPlanilla.findByAFechaCrea", query = "SELECT r FROM RegPlanPlanilla r WHERE r.aFechaCrea = :aFechaCrea"),
    @NamedQuery(name = "RegPlanPlanilla.findByAUsuarioCrea", query = "SELECT r FROM RegPlanPlanilla r WHERE r.aUsuarioCrea = :aUsuarioCrea"),
    @NamedQuery(name = "RegPlanPlanilla.findByAFechaModificacion", query = "SELECT r FROM RegPlanPlanilla r WHERE r.aFechaModificacion = :aFechaModificacion"),
    @NamedQuery(name = "RegPlanPlanilla.findByAUsuarioModifica", query = "SELECT r FROM RegPlanPlanilla r WHERE r.aUsuarioModifica = :aUsuarioModifica")})
public class RegPlanPlanilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegPlanPlanillaPK regPlanPlanillaPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PLAN_SALARIO_BASE", precision = 10, scale = 2)
    private BigDecimal planSalarioBase;
    @Column(name = "PLAN_MONTO_DESCUENTO_AFP", precision = 10, scale = 2)
    private BigDecimal planMontoDescuentoAfp;
    @Column(name = "PLAN_MONTO_DESCUENTO_ISSS", precision = 10, scale = 2)
    private BigDecimal planMontoDescuentoIsss;
    @Column(name = "PLAN_MONTO_DESCUENTO_RENTA", precision = 10, scale = 2)
    private BigDecimal planMontoDescuentoRenta;
    @Column(name = "PLAN_SALARIO_NETO", precision = 10, scale = 2)
    private BigDecimal planSalarioNeto;
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
    @JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RegEmpEmpleado regEmpEmpleado;

    public RegPlanPlanilla() {
    }

    public RegPlanPlanilla(RegPlanPlanillaPK regPlanPlanillaPK) {
        this.regPlanPlanillaPK = regPlanPlanillaPK;
    }

    public RegPlanPlanilla(int empId, Date planFechaPlanilla) {
        this.regPlanPlanillaPK = new RegPlanPlanillaPK(empId, planFechaPlanilla);
    }

    public RegPlanPlanillaPK getRegPlanPlanillaPK() {
        return regPlanPlanillaPK;
    }

    public void setRegPlanPlanillaPK(RegPlanPlanillaPK regPlanPlanillaPK) {
        this.regPlanPlanillaPK = regPlanPlanillaPK;
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

    public RegEmpEmpleado getRegEmpEmpleado() {
        return regEmpEmpleado;
    }

    public void setRegEmpEmpleado(RegEmpEmpleado regEmpEmpleado) {
        this.regEmpEmpleado = regEmpEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regPlanPlanillaPK != null ? regPlanPlanillaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegPlanPlanilla)) {
            return false;
        }
        RegPlanPlanilla other = (RegPlanPlanilla) object;
        if ((this.regPlanPlanillaPK == null && other.regPlanPlanillaPK != null) || (this.regPlanPlanillaPK != null && !this.regPlanPlanillaPK.equals(other.regPlanPlanillaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.echo.bitlab.jpa.RegPlanPlanilla[ regPlanPlanillaPK=" + regPlanPlanillaPK + " ]";
    }
    
}
