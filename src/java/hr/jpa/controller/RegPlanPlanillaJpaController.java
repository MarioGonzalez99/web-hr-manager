/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.controller;

import hr.jpa.controller.exceptions.NonexistentEntityException;
import hr.jpa.controller.exceptions.PreexistingEntityException;
import hr.jpa.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hr.jpa.entities.RegEmpEmpleado;
import hr.jpa.entities.RegPlanPlanilla;
import hr.jpa.entities.RegPlanPlanillaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mario
 */
public class RegPlanPlanillaJpaController implements Serializable {

    public RegPlanPlanillaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegPlanPlanilla regPlanPlanilla) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (regPlanPlanilla.getRegPlanPlanillaPK() == null) {
            regPlanPlanilla.setRegPlanPlanillaPK(new RegPlanPlanillaPK());
        }
        regPlanPlanilla.getRegPlanPlanillaPK().setEmpId(regPlanPlanilla.getRegEmpEmpleado().getEmpId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegEmpEmpleado regEmpEmpleado = regPlanPlanilla.getRegEmpEmpleado();
            if (regEmpEmpleado != null) {
                regEmpEmpleado = em.getReference(regEmpEmpleado.getClass(), regEmpEmpleado.getEmpId());
                regPlanPlanilla.setRegEmpEmpleado(regEmpEmpleado);
            }
            em.persist(regPlanPlanilla);
            if (regEmpEmpleado != null) {
                regEmpEmpleado.getRegPlanPlanillaList().add(regPlanPlanilla);
                regEmpEmpleado = em.merge(regEmpEmpleado);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegPlanPlanilla(regPlanPlanilla.getRegPlanPlanillaPK()) != null) {
                throw new PreexistingEntityException("RegPlanPlanilla " + regPlanPlanilla + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegPlanPlanilla regPlanPlanilla) throws NonexistentEntityException, RollbackFailureException, Exception {
        regPlanPlanilla.getRegPlanPlanillaPK().setEmpId(regPlanPlanilla.getRegEmpEmpleado().getEmpId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegPlanPlanilla persistentRegPlanPlanilla = em.find(RegPlanPlanilla.class, regPlanPlanilla.getRegPlanPlanillaPK());
            RegEmpEmpleado regEmpEmpleadoOld = persistentRegPlanPlanilla.getRegEmpEmpleado();
            RegEmpEmpleado regEmpEmpleadoNew = regPlanPlanilla.getRegEmpEmpleado();
            if (regEmpEmpleadoNew != null) {
                regEmpEmpleadoNew = em.getReference(regEmpEmpleadoNew.getClass(), regEmpEmpleadoNew.getEmpId());
                regPlanPlanilla.setRegEmpEmpleado(regEmpEmpleadoNew);
            }
            regPlanPlanilla = em.merge(regPlanPlanilla);
            if (regEmpEmpleadoOld != null && !regEmpEmpleadoOld.equals(regEmpEmpleadoNew)) {
                regEmpEmpleadoOld.getRegPlanPlanillaList().remove(regPlanPlanilla);
                regEmpEmpleadoOld = em.merge(regEmpEmpleadoOld);
            }
            if (regEmpEmpleadoNew != null && !regEmpEmpleadoNew.equals(regEmpEmpleadoOld)) {
                regEmpEmpleadoNew.getRegPlanPlanillaList().add(regPlanPlanilla);
                regEmpEmpleadoNew = em.merge(regEmpEmpleadoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RegPlanPlanillaPK id = regPlanPlanilla.getRegPlanPlanillaPK();
                if (findRegPlanPlanilla(id) == null) {
                    throw new NonexistentEntityException("The regPlanPlanilla with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RegPlanPlanillaPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegPlanPlanilla regPlanPlanilla;
            try {
                regPlanPlanilla = em.getReference(RegPlanPlanilla.class, id);
                regPlanPlanilla.getRegPlanPlanillaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regPlanPlanilla with id " + id + " no longer exists.", enfe);
            }
            RegEmpEmpleado regEmpEmpleado = regPlanPlanilla.getRegEmpEmpleado();
            if (regEmpEmpleado != null) {
                regEmpEmpleado.getRegPlanPlanillaList().remove(regPlanPlanilla);
                regEmpEmpleado = em.merge(regEmpEmpleado);
            }
            em.remove(regPlanPlanilla);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegPlanPlanilla> findRegPlanPlanillaEntities() {
        return findRegPlanPlanillaEntities(true, -1, -1);
    }

    public List<RegPlanPlanilla> findRegPlanPlanillaEntities(int maxResults, int firstResult) {
        return findRegPlanPlanillaEntities(false, maxResults, firstResult);
    }

    private List<RegPlanPlanilla> findRegPlanPlanillaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegPlanPlanilla.class));
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

    public RegPlanPlanilla findRegPlanPlanilla(RegPlanPlanillaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegPlanPlanilla.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegPlanPlanillaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegPlanPlanilla> rt = cq.from(RegPlanPlanilla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
