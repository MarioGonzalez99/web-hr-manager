/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.managedBeans;

import hr.jpa.controller.EmpleadoJpaController;
import hr.jpa.entity.Empleado;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Mario
 */
@Named(value = "employeesMB")
@ViewScoped
public class EmployeesMB implements Serializable {

    /**
     * Creates a new instance of EmployeesMB
     */
    private EmpleadoJpaController employeeController;

    private List<Empleado> employees;
    private Empleado selectedEmployee;

    public EmployeesMB() {
    }

    @PostConstruct
    public void init() {
        employeeController = new EmpleadoJpaController();
        employees = employeeController.findEmpleadoEntities();
    }

    public List<Empleado> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Empleado> employees) {
        this.employees = employees;
    }

    public Empleado getSelectedEmployee() {
        return selectedEmployee;
    }

    public void setSelectedEmployee(Empleado selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }

    public void clearMultiViewState() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        PrimeFaces.current().multiViewState().clearAll(viewId, true, (clientId) -> {
            showMessage(clientId);
        });
    }

    private void showMessage(String clientId) {
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, clientId + " multiview state has been cleared out", null));
    }
}
