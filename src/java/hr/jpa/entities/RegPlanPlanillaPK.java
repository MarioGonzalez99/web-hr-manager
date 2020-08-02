/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Mario
 */
@Embeddable
public class RegPlanPlanillaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_ID", nullable = false)
    private int empId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PLAN_FECHA_PLANILLA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date planFechaPlanilla;

    public RegPlanPlanillaPK() {
    }

    public RegPlanPlanillaPK(int empId, Date planFechaPlanilla) {
        this.empId = empId;
        this.planFechaPlanilla = planFechaPlanilla;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Date getPlanFechaPlanilla() {
        return planFechaPlanilla;
    }

    public void setPlanFechaPlanilla(Date planFechaPlanilla) {
        this.planFechaPlanilla = planFechaPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empId;
        hash += (planFechaPlanilla != null ? planFechaPlanilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegPlanPlanillaPK)) {
            return false;
        }
        RegPlanPlanillaPK other = (RegPlanPlanillaPK) object;
        if (this.empId != other.empId) {
            return false;
        }
        if ((this.planFechaPlanilla == null && other.planFechaPlanilla != null) || (this.planFechaPlanilla != null && !this.planFechaPlanilla.equals(other.planFechaPlanilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.echo.bitlab.jpa.RegPlanPlanillaPK[ empId=" + empId + ", planFechaPlanilla=" + planFechaPlanilla + " ]";
    }
    
}
