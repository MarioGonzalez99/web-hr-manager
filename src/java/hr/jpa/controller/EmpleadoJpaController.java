/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.controller;

import hr.connection.EMF;
import hr.jpa.controller.exceptions.IllegalOrphanException;
import hr.jpa.controller.exceptions.NonexistentEntityException;
import hr.jpa.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hr.jpa.entity.Departamento;
import hr.jpa.entity.Empleado;
import hr.jpa.entity.EstadoEmpleado;
import hr.jpa.entity.Planilla;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mario
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController() {
        EntityManagerFactory emfac = EMF.getInstance().getEntityManagerFactory();
        this.emf = emfac;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getPlanillaList() == null) {
            empleado.setPlanillaList(new ArrayList<Planilla>());
        }
        if (empleado.getEmpleadoList() == null) {
            empleado.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento depNombre = empleado.getDepNombre();
            if (depNombre != null) {
                depNombre = em.getReference(depNombre.getClass(), depNombre.getDepNombre());
                empleado.setDepNombre(depNombre);
            }
            Empleado empIdJefe = empleado.getEmpIdJefe();
            if (empIdJefe != null) {
                empIdJefe = em.getReference(empIdJefe.getClass(), empIdJefe.getEmpId());
                empleado.setEmpIdJefe(empIdJefe);
            }
            EstadoEmpleado estId = empleado.getEstId();
            if (estId != null) {
                estId = em.getReference(estId.getClass(), estId.getEstId());
                empleado.setEstId(estId);
            }
            List<Planilla> attachedPlanillaList = new ArrayList<Planilla>();
            for (Planilla planillaListPlanillaToAttach : empleado.getPlanillaList()) {
                planillaListPlanillaToAttach = em.getReference(planillaListPlanillaToAttach.getClass(), planillaListPlanillaToAttach.getPlanillaPK());
                attachedPlanillaList.add(planillaListPlanillaToAttach);
            }
            empleado.setPlanillaList(attachedPlanillaList);
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : empleado.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmpId());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            empleado.setEmpleadoList(attachedEmpleadoList);
            em.persist(empleado);
            if (depNombre != null) {
                depNombre.getEmpleadoList().add(empleado);
                depNombre = em.merge(depNombre);
            }
            if (empIdJefe != null) {
                empIdJefe.getEmpleadoList().add(empleado);
                empIdJefe = em.merge(empIdJefe);
            }
            if (estId != null) {
                estId.getEmpleadoList().add(empleado);
                estId = em.merge(estId);
            }
            for (Planilla planillaListPlanilla : empleado.getPlanillaList()) {
                Empleado oldEmpleadoOfPlanillaListPlanilla = planillaListPlanilla.getEmpleado();
                planillaListPlanilla.setEmpleado(empleado);
                planillaListPlanilla = em.merge(planillaListPlanilla);
                if (oldEmpleadoOfPlanillaListPlanilla != null) {
                    oldEmpleadoOfPlanillaListPlanilla.getPlanillaList().remove(planillaListPlanilla);
                    oldEmpleadoOfPlanillaListPlanilla = em.merge(oldEmpleadoOfPlanillaListPlanilla);
                }
            }
            for (Empleado empleadoListEmpleado : empleado.getEmpleadoList()) {
                Empleado oldEmpIdJefeOfEmpleadoListEmpleado = empleadoListEmpleado.getEmpIdJefe();
                empleadoListEmpleado.setEmpIdJefe(empleado);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldEmpIdJefeOfEmpleadoListEmpleado != null) {
                    oldEmpIdJefeOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldEmpIdJefeOfEmpleadoListEmpleado = em.merge(oldEmpIdJefeOfEmpleadoListEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getEmpId()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmpId());
            Departamento depNombreOld = persistentEmpleado.getDepNombre();
            Departamento depNombreNew = empleado.getDepNombre();
            Empleado empIdJefeOld = persistentEmpleado.getEmpIdJefe();
            Empleado empIdJefeNew = empleado.getEmpIdJefe();
            EstadoEmpleado estIdOld = persistentEmpleado.getEstId();
            EstadoEmpleado estIdNew = empleado.getEstId();
            List<Planilla> planillaListOld = persistentEmpleado.getPlanillaList();
            List<Planilla> planillaListNew = empleado.getPlanillaList();
            List<Empleado> empleadoListOld = persistentEmpleado.getEmpleadoList();
            List<Empleado> empleadoListNew = empleado.getEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (Planilla planillaListOldPlanilla : planillaListOld) {
                if (!planillaListNew.contains(planillaListOldPlanilla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Planilla " + planillaListOldPlanilla + " since its empleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (depNombreNew != null) {
                depNombreNew = em.getReference(depNombreNew.getClass(), depNombreNew.getDepNombre());
                empleado.setDepNombre(depNombreNew);
            }
            if (empIdJefeNew != null) {
                empIdJefeNew = em.getReference(empIdJefeNew.getClass(), empIdJefeNew.getEmpId());
                empleado.setEmpIdJefe(empIdJefeNew);
            }
            if (estIdNew != null) {
                estIdNew = em.getReference(estIdNew.getClass(), estIdNew.getEstId());
                empleado.setEstId(estIdNew);
            }
            List<Planilla> attachedPlanillaListNew = new ArrayList<Planilla>();
            for (Planilla planillaListNewPlanillaToAttach : planillaListNew) {
                planillaListNewPlanillaToAttach = em.getReference(planillaListNewPlanillaToAttach.getClass(), planillaListNewPlanillaToAttach.getPlanillaPK());
                attachedPlanillaListNew.add(planillaListNewPlanillaToAttach);
            }
            planillaListNew = attachedPlanillaListNew;
            empleado.setPlanillaList(planillaListNew);
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmpId());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            empleado.setEmpleadoList(empleadoListNew);
            empleado = em.merge(empleado);
            if (depNombreOld != null && !depNombreOld.equals(depNombreNew)) {
                depNombreOld.getEmpleadoList().remove(empleado);
                depNombreOld = em.merge(depNombreOld);
            }
            if (depNombreNew != null && !depNombreNew.equals(depNombreOld)) {
                depNombreNew.getEmpleadoList().add(empleado);
                depNombreNew = em.merge(depNombreNew);
            }
            if (empIdJefeOld != null && !empIdJefeOld.equals(empIdJefeNew)) {
                empIdJefeOld.getEmpleadoList().remove(empleado);
                empIdJefeOld = em.merge(empIdJefeOld);
            }
            if (empIdJefeNew != null && !empIdJefeNew.equals(empIdJefeOld)) {
                empIdJefeNew.getEmpleadoList().add(empleado);
                empIdJefeNew = em.merge(empIdJefeNew);
            }
            if (estIdOld != null && !estIdOld.equals(estIdNew)) {
                estIdOld.getEmpleadoList().remove(empleado);
                estIdOld = em.merge(estIdOld);
            }
            if (estIdNew != null && !estIdNew.equals(estIdOld)) {
                estIdNew.getEmpleadoList().add(empleado);
                estIdNew = em.merge(estIdNew);
            }
            for (Planilla planillaListNewPlanilla : planillaListNew) {
                if (!planillaListOld.contains(planillaListNewPlanilla)) {
                    Empleado oldEmpleadoOfPlanillaListNewPlanilla = planillaListNewPlanilla.getEmpleado();
                    planillaListNewPlanilla.setEmpleado(empleado);
                    planillaListNewPlanilla = em.merge(planillaListNewPlanilla);
                    if (oldEmpleadoOfPlanillaListNewPlanilla != null && !oldEmpleadoOfPlanillaListNewPlanilla.equals(empleado)) {
                        oldEmpleadoOfPlanillaListNewPlanilla.getPlanillaList().remove(planillaListNewPlanilla);
                        oldEmpleadoOfPlanillaListNewPlanilla = em.merge(oldEmpleadoOfPlanillaListNewPlanilla);
                    }
                }
            }
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.setEmpIdJefe(null);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Empleado oldEmpIdJefeOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getEmpIdJefe();
                    empleadoListNewEmpleado.setEmpIdJefe(empleado);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldEmpIdJefeOfEmpleadoListNewEmpleado != null && !oldEmpIdJefeOfEmpleadoListNewEmpleado.equals(empleado)) {
                        oldEmpIdJefeOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldEmpIdJefeOfEmpleadoListNewEmpleado = em.merge(oldEmpIdJefeOfEmpleadoListNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getEmpId();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getEmpId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Planilla> planillaListOrphanCheck = empleado.getPlanillaList();
            for (Planilla planillaListOrphanCheckPlanilla : planillaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleado (" + empleado + ") cannot be destroyed since the Planilla " + planillaListOrphanCheckPlanilla + " in its planillaList field has a non-nullable empleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento depNombre = empleado.getDepNombre();
            if (depNombre != null) {
                depNombre.getEmpleadoList().remove(empleado);
                depNombre = em.merge(depNombre);
            }
            Empleado empIdJefe = empleado.getEmpIdJefe();
            if (empIdJefe != null) {
                empIdJefe.getEmpleadoList().remove(empleado);
                empIdJefe = em.merge(empIdJefe);
            }
            EstadoEmpleado estId = empleado.getEstId();
            if (estId != null) {
                estId.getEmpleadoList().remove(empleado);
                estId = em.merge(estId);
            }
            List<Empleado> empleadoList = empleado.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.setEmpIdJefe(null);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
