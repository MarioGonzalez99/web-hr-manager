/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

import hr.jpa.controller.PlanillaJpaController;
import hr.jpa.entity.Planilla;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Mario
 */
@Named(value = "payrollVisMB")
@ViewScoped
public class payrollVisMB implements Serializable{

    /**
     * Creates a new instance of payrollVisMB
     */
    
    @Inject
    UserSessionMB user;
    
    private PlanillaJpaController payrollController;

    private List<Planilla> payrolls;
    private List<Planilla> payrollsFiltered;

    
    public payrollVisMB() {
    }
    
    @PostConstruct
    public void init(){
        payrollController = new PlanillaJpaController();
        payrolls = payrollController.findPlanillaEntities();
    }
    
    public List<Planilla> getPayrolls() {
        return payrolls;
    }

    public void setPayrolls(List<Planilla> payrolls) {
        this.payrolls = payrolls;
    }

    public List<Planilla> getPayrollsFiltered() {
        return payrollsFiltered;
    }

    public void setPayrollsFiltered(List<Planilla> payrollsFiltered) {
        this.payrollsFiltered = payrollsFiltered;
    }
}
