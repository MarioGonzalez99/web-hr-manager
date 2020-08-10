/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

import hr.jpa.controller.EmpleadoJpaController;
import hr.jpa.controller.PlanillaJpaController;
import hr.jpa.entity.Departamento;
import hr.jpa.entity.Empleado;
import hr.jpa.entity.EstadoEmpleado;
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
@Named(value = "employeeCreationMB")
@ViewScoped
public class EmployeeCreationMB implements Serializable{

    /**
     * Creates a new instance of newEmployeeMB
     */
    
    @Inject
    UserSessionMB user;
    
    private EmpleadoJpaController employeeController;

    private Empleado employee;
    private String salary;
    private Date paydate;
    
    public EmployeeCreationMB() {
    }
    
    @PostConstruct
    public void init(){
        employeeController = new EmpleadoJpaController();
        employee = new Empleado();
        employee.setDepNombre(new Departamento());
        employee.setEstId(new EstadoEmpleado());
        employee.setEmpIdJefe(new Empleado());
    }
    
    public Empleado getEmployee() {
        return employee;
    }

    public void setEmployee(Empleado employee) {
        this.employee = employee;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }
    
    public void createNewEmployee(){
        try {
            employee.setAUsuarioCrea(user.getUser().getUsername());
            employee.setAFechaCrea(Calendar.getInstance().getTime());
            employeeController.create(employee);
            ControllerUtilities.sendMessageInfo("Empleado Ingresado", "Se ha ingresado al empleado correctamente dentro de la base de datos");
        } catch (Exception ex) {
            Logger.getLogger(EmployeesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
