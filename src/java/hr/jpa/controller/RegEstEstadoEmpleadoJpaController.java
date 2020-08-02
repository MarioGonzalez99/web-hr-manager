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
import hr.jpa.entities.RegEstEstadoEmpleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mario
 */
public class RegEstEstadoEmpleadoJpaController implements Serializable {

    public RegEstEstadoEmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegEstEstadoEmpleado regEstEstadoEmpleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (regEstEstadoEmpleado.getRegEmpEmpleadoList() == null) {
            regEstEstadoEmpleado.setRegEmpEmpleadoList(new ArrayList<RegEmpEmpleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RegEmpEmpleado> attachedRegEmpEmpleadoList = new ArrayList<RegEmpEmpleado>();
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleadoToAttach : regEstEstadoEmpleado.getRegEmpEmpleadoList()) {
                regEmpEmpleadoListRegEmpEmpleadoToAttach = em.getReference(regEmpEmpleadoListRegEmpEmpleadoToAttach.getClass(), regEmpEmpleadoListRegEmpEmpleadoToAttach.getEmpId());
                attachedRegEmpEmpleadoList.add(regEmpEmpleadoListRegEmpEmpleadoToAttach);
            }
            regEstEstadoEmpleado.setRegEmpEmpleadoList(attachedRegEmpEmpleadoList);
            em.persist(regEstEstadoEmpleado);
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleado : regEstEstadoEmpleado.getRegEmpEmpleadoList()) {
                RegEstEstadoEmpleado oldEstIdOfRegEmpEmpleadoListRegEmpEmpleado = regEmpEmpleadoListRegEmpEmpleado.getEstId();
                regEmpEmpleadoListRegEmpEmpleado.setEstId(regEstEstadoEmpleado);
                regEmpEmpleadoListRegEmpEmpleado = em.merge(regEmpEmpleadoListRegEmpEmpleado);
                if (oldEstIdOfRegEmpEmpleadoListRegEmpEmpleado != null) {
                    oldEstIdOfRegEmpEmpleadoListRegEmpEmpleado.getRegEmpEmpleadoList().remove(regEmpEmpleadoListRegEmpEmpleado);
                    oldEstIdOfRegEmpEmpleadoListRegEmpEmpleado = em.merge(oldEstIdOfRegEmpEmpleadoListRegEmpEmpleado);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegEstEstadoEmpleado(regEstEstadoEmpleado.getEstId()) != null) {
                throw new PreexistingEntityException("RegEstEstadoEmpleado " + regEstEstadoEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegEstEstadoEmpleado regEstEstadoEmpleado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegEstEstadoEmpleado persistentRegEstEstadoEmpleado = em.find(RegEstEstadoEmpleado.class, regEstEstadoEmpleado.getEstId());
            List<RegEmpEmpleado> regEmpEmpleadoListOld = persistentRegEstEstadoEmpleado.getRegEmpEmpleadoList();
            List<RegEmpEmpleado> regEmpEmpleadoListNew = regEstEstadoEmpleado.getRegEmpEmpleadoList();
            List<RegEmpEmpleado> attachedRegEmpEmpleadoListNew = new ArrayList<RegEmpEmpleado>();
            for (RegEmpEmpleado regEmpEmpleadoListNewRegEmpEmpleadoToAttach : regEmpEmpleadoListNew) {
                regEmpEmpleadoListNewRegEmpEmpleadoToAttach = em.getReference(regEmpEmpleadoListNewRegEmpEmpleadoToAttach.getClass(), regEmpEmpleadoListNewRegEmpEmpleadoToAttach.getEmpId());
                attachedRegEmpEmpleadoListNew.add(regEmpEmpleadoListNewRegEmpEmpleadoToAttach);
            }
            regEmpEmpleadoListNew = attachedRegEmpEmpleadoListNew;
            regEstEstadoEmpleado.setRegEmpEmpleadoList(regEmpEmpleadoListNew);
            regEstEstadoEmpleado = em.merge(regEstEstadoEmpleado);
            for (RegEmpEmpleado regEmpEmpleadoListOldRegEmpEmpleado : regEmpEmpleadoListOld) {
                if (!regEmpEmpleadoListNew.contains(regEmpEmpleadoListOldRegEmpEmpleado)) {
                    regEmpEmpleadoListOldRegEmpEmpleado.setEstId(null);
                    regEmpEmpleadoListOldRegEmpEmpleado = em.merge(regEmpEmpleadoListOldRegEmpEmpleado);
                }
            }
            for (RegEmpEmpleado regEmpEmpleadoListNewRegEmpEmpleado : regEmpEmpleadoListNew) {
                if (!regEmpEmpleadoListOld.contains(regEmpEmpleadoListNewRegEmpEmpleado)) {
                    RegEstEstadoEmpleado oldEstIdOfRegEmpEmpleadoListNewRegEmpEmpleado = regEmpEmpleadoListNewRegEmpEmpleado.getEstId();
                    regEmpEmpleadoListNewRegEmpEmpleado.setEstId(regEstEstadoEmpleado);
                    regEmpEmpleadoListNewRegEmpEmpleado = em.merge(regEmpEmpleadoListNewRegEmpEmpleado);
                    if (oldEstIdOfRegEmpEmpleadoListNewRegEmpEmpleado != null && !oldEstIdOfRegEmpEmpleadoListNewRegEmpEmpleado.equals(regEstEstadoEmpleado)) {
                        oldEstIdOfRegEmpEmpleadoListNewRegEmpEmpleado.getRegEmpEmpleadoList().remove(regEmpEmpleadoListNewRegEmpEmpleado);
                        oldEstIdOfRegEmpEmpleadoListNewRegEmpEmpleado = em.merge(oldEstIdOfRegEmpEmpleadoListNewRegEmpEmpleado);
                    }
                }
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
                String id = regEstEstadoEmpleado.getEstId();
                if (findRegEstEstadoEmpleado(id) == null) {
                    throw new NonexistentEntityException("The regEstEstadoEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegEstEstadoEmpleado regEstEstadoEmpleado;
            try {
                regEstEstadoEmpleado = em.getReference(RegEstEstadoEmpleado.class, id);
                regEstEstadoEmpleado.getEstId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regEstEstadoEmpleado with id " + id + " no longer exists.", enfe);
            }
            List<RegEmpEmpleado> regEmpEmpleadoList = regEstEstadoEmpleado.getRegEmpEmpleadoList();
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleado : regEmpEmpleadoList) {
                regEmpEmpleadoListRegEmpEmpleado.setEstId(null);
                regEmpEmpleadoListRegEmpEmpleado = em.merge(regEmpEmpleadoListRegEmpEmpleado);
            }
            em.remove(regEstEstadoEmpleado);
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

    public List<RegEstEstadoEmpleado> findRegEstEstadoEmpleadoEntities() {
        return findRegEstEstadoEmpleadoEntities(true, -1, -1);
    }

    public List<RegEstEstadoEmpleado> findRegEstEstadoEmpleadoEntities(int maxResults, int firstResult) {
        return findRegEstEstadoEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<RegEstEstadoEmpleado> findRegEstEstadoEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegEstEstadoEmpleado.class));
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

    public RegEstEstadoEmpleado findRegEstEstadoEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegEstEstadoEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegEstEstadoEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegEstEstadoEmpleado> rt = cq.from(RegEstEstadoEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
