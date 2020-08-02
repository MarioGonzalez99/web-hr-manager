/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.controller;

import hr.connection.EMF;
import hr.jpa.controller.exceptions.NonexistentEntityException;
import hr.jpa.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hr.jpa.entity.Empleado;
import hr.jpa.entity.Planilla;
import hr.jpa.entity.PlanillaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mario
 */
public class PlanillaJpaController implements Serializable {

    public PlanillaJpaController() {
        EntityManagerFactory emfac = EMF.getInstance().getEntityManagerFactory();
        this.emf = emfac;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Planilla planilla) throws PreexistingEntityException, Exception {
        if (planilla.getPlanillaPK() == null) {
            planilla.setPlanillaPK(new PlanillaPK());
        }
        planilla.getPlanillaPK().setEmpId(planilla.getEmpleado().getEmpId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = planilla.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getEmpId());
                planilla.setEmpleado(empleado);
            }
            em.persist(planilla);
            if (empleado != null) {
                empleado.getPlanillaList().add(planilla);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlanilla(planilla.getPlanillaPK()) != null) {
                throw new PreexistingEntityException("Planilla " + planilla + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Planilla planilla) throws NonexistentEntityException, Exception {
        planilla.getPlanillaPK().setEmpId(planilla.getEmpleado().getEmpId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planilla persistentPlanilla = em.find(Planilla.class, planilla.getPlanillaPK());
            Empleado empleadoOld = persistentPlanilla.getEmpleado();
            Empleado empleadoNew = planilla.getEmpleado();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getEmpId());
                planilla.setEmpleado(empleadoNew);
            }
            planilla = em.merge(planilla);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getPlanillaList().remove(planilla);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getPlanillaList().add(planilla);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PlanillaPK id = planilla.getPlanillaPK();
                if (findPlanilla(id) == null) {
                    throw new NonexistentEntityException("The planilla with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PlanillaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Planilla planilla;
            try {
                planilla = em.getReference(Planilla.class, id);
                planilla.getPlanillaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The planilla with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = planilla.getEmpleado();
            if (empleado != null) {
                empleado.getPlanillaList().remove(planilla);
                empleado = em.merge(empleado);
            }
            em.remove(planilla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Planilla> findPlanillaEntities() {
        return findPlanillaEntities(true, -1, -1);
    }

    public List<Planilla> findPlanillaEntities(int maxResults, int firstResult) {
        return findPlanillaEntities(false, maxResults, firstResult);
    }

    private List<Planilla> findPlanillaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Planilla.class));
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

    public Planilla findPlanilla(PlanillaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Planilla.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlanillaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Planilla> rt = cq.from(Planilla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
