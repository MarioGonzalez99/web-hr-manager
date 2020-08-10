/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

import hr.jpa.controller.PlanillaJpaController;
import hr.jpa.entity.Planilla;
import hr.model.ControllerUtilities;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Mario
 */
@Named(value = "payrollMB")
@ViewScoped
public class payrollMB implements Serializable{

    /**
     * Creates a new instance of payrollMB
     */
    @Inject
    UserSessionMB user;
    
    private PlanillaJpaController payrollController;
    
    private int employeeId;
    private Date paydate;
    private String salary;
    private Planilla payroll;
    
    public payrollMB() {
    }
    
    @PostConstruct
    public void init(){
        payrollController = new PlanillaJpaController();
    }
    
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
    
    public void assignPayroll(){
        try {
            payroll = new Planilla(employeeId, paydate, new BigDecimal(salary));
            payroll.setAUsuarioCrea(user.getUser().getUsername());
            payroll.setAFechaCrea(Calendar.getInstance().getTime());
            payrollController.create(payroll);
            ControllerUtilities.sendMessageInfo("Planilla Ingresada", "Se ha asignado correctamente la planilla al empleado");
        } catch (Exception ex) {
            Logger.getLogger(payrollMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
