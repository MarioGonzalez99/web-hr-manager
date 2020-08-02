/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.controller;

import hr.jpa.controller.exceptions.NonexistentEntityException;
import hr.jpa.controller.exceptions.PreexistingEntityException;
import hr.jpa.controller.exceptions.RollbackFailureException;
import hr.jpa.entities.RegDepDepartamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hr.jpa.entities.RegEmpEmpleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mario
 */
public class RegDepDepartamentoJpaController implements Serializable {

    public RegDepDepartamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegDepDepartamento regDepDepartamento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (regDepDepartamento.getRegEmpEmpleadoList() == null) {
            regDepDepartamento.setRegEmpEmpleadoList(new ArrayList<RegEmpEmpleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RegEmpEmpleado> attachedRegEmpEmpleadoList = new ArrayList<RegEmpEmpleado>();
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleadoToAttach : regDepDepartamento.getRegEmpEmpleadoList()) {
                regEmpEmpleadoListRegEmpEmpleadoToAttach = em.getReference(regEmpEmpleadoListRegEmpEmpleadoToAttach.getClass(), regEmpEmpleadoListRegEmpEmpleadoToAttach.getEmpId());
                attachedRegEmpEmpleadoList.add(regEmpEmpleadoListRegEmpEmpleadoToAttach);
            }
            regDepDepartamento.setRegEmpEmpleadoList(attachedRegEmpEmpleadoList);
            em.persist(regDepDepartamento);
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleado : regDepDepartamento.getRegEmpEmpleadoList()) {
                RegDepDepartamento oldDepNombreOfRegEmpEmpleadoListRegEmpEmpleado = regEmpEmpleadoListRegEmpEmpleado.getDepNombre();
                regEmpEmpleadoListRegEmpEmpleado.setDepNombre(regDepDepartamento);
                regEmpEmpleadoListRegEmpEmpleado = em.merge(regEmpEmpleadoListRegEmpEmpleado);
                if (oldDepNombreOfRegEmpEmpleadoListRegEmpEmpleado != null) {
                    oldDepNombreOfRegEmpEmpleadoListRegEmpEmpleado.getRegEmpEmpleadoList().remove(regEmpEmpleadoListRegEmpEmpleado);
                    oldDepNombreOfRegEmpEmpleadoListRegEmpEmpleado = em.merge(oldDepNombreOfRegEmpEmpleadoListRegEmpEmpleado);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegDepDepartamento(regDepDepartamento.getDepNombre()) != null) {
                throw new PreexistingEntityException("RegDepDepartamento " + regDepDepartamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegDepDepartamento regDepDepartamento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegDepDepartamento persistentRegDepDepartamento = em.find(RegDepDepartamento.class, regDepDepartamento.getDepNombre());
            List<RegEmpEmpleado> regEmpEmpleadoListOld = persistentRegDepDepartamento.getRegEmpEmpleadoList();
            List<RegEmpEmpleado> regEmpEmpleadoListNew = regDepDepartamento.getRegEmpEmpleadoList();
            List<RegEmpEmpleado> attachedRegEmpEmpleadoListNew = new ArrayList<RegEmpEmpleado>();
            for (RegEmpEmpleado regEmpEmpleadoListNewRegEmpEmpleadoToAttach : regEmpEmpleadoListNew) {
                regEmpEmpleadoListNewRegEmpEmpleadoToAttach = em.getReference(regEmpEmpleadoListNewRegEmpEmpleadoToAttach.getClass(), regEmpEmpleadoListNewRegEmpEmpleadoToAttach.getEmpId());
                attachedRegEmpEmpleadoListNew.add(regEmpEmpleadoListNewRegEmpEmpleadoToAttach);
            }
            regEmpEmpleadoListNew = attachedRegEmpEmpleadoListNew;
            regDepDepartamento.setRegEmpEmpleadoList(regEmpEmpleadoListNew);
            regDepDepartamento = em.merge(regDepDepartamento);
            for (RegEmpEmpleado regEmpEmpleadoListOldRegEmpEmpleado : regEmpEmpleadoListOld) {
                if (!regEmpEmpleadoListNew.contains(regEmpEmpleadoListOldRegEmpEmpleado)) {
                    regEmpEmpleadoListOldRegEmpEmpleado.setDepNombre(null);
                    regEmpEmpleadoListOldRegEmpEmpleado = em.merge(regEmpEmpleadoListOldRegEmpEmpleado);
                }
            }
            for (RegEmpEmpleado regEmpEmpleadoListNewRegEmpEmpleado : regEmpEmpleadoListNew) {
                if (!regEmpEmpleadoListOld.contains(regEmpEmpleadoListNewRegEmpEmpleado)) {
                    RegDepDepartamento oldDepNombreOfRegEmpEmpleadoListNewRegEmpEmpleado = regEmpEmpleadoListNewRegEmpEmpleado.getDepNombre();
                    regEmpEmpleadoListNewRegEmpEmpleado.setDepNombre(regDepDepartamento);
                    regEmpEmpleadoListNewRegEmpEmpleado = em.merge(regEmpEmpleadoListNewRegEmpEmpleado);
                    if (oldDepNombreOfRegEmpEmpleadoListNewRegEmpEmpleado != null && !oldDepNombreOfRegEmpEmpleadoListNewRegEmpEmpleado.equals(regDepDepartamento)) {
                        oldDepNombreOfRegEmpEmpleadoListNewRegEmpEmpleado.getRegEmpEmpleadoList().remove(regEmpEmpleadoListNewRegEmpEmpleado);
                        oldDepNombreOfRegEmpEmpleadoListNewRegEmpEmpleado = em.merge(oldDepNombreOfRegEmpEmpleadoListNewRegEmpEmpleado);
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
                String id = regDepDepartamento.getDepNombre();
                if (findRegDepDepartamento(id) == null) {
                    throw new NonexistentEntityException("The regDepDepartamento with id " + id + " no longer exists.");
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
            RegDepDepartamento regDepDepartamento;
            try {
                regDepDepartamento = em.getReference(RegDepDepartamento.class, id);
                regDepDepartamento.getDepNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regDepDepartamento with id " + id + " no longer exists.", enfe);
            }
            List<RegEmpEmpleado> regEmpEmpleadoList = regDepDepartamento.getRegEmpEmpleadoList();
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleado : regEmpEmpleadoList) {
                regEmpEmpleadoListRegEmpEmpleado.setDepNombre(null);
                regEmpEmpleadoListRegEmpEmpleado = em.merge(regEmpEmpleadoListRegEmpEmpleado);
            }
            em.remove(regDepDepartamento);
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

    public List<RegDepDepartamento> findRegDepDepartamentoEntities() {
        return findRegDepDepartamentoEntities(true, -1, -1);
    }

    public List<RegDepDepartamento> findRegDepDepartamentoEntities(int maxResults, int firstResult) {
        return findRegDepDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<RegDepDepartamento> findRegDepDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegDepDepartamento.class));
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

    public RegDepDepartamento findRegDepDepartamento(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegDepDepartamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegDepDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegDepDepartamento> rt = cq.from(RegDepDepartamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
