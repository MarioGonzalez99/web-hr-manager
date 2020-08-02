/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.controller;

import hr.jpa.controller.exceptions.NonexistentEntityException;
import hr.jpa.controller.exceptions.PreexistingEntityException;
import hr.jpa.controller.exceptions.RollbackFailureException;
import hr.jpa.entities.UsrUsuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mario
 */
public class UsrUsuarioJpaController implements Serializable {

    public UsrUsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsrUsuario usrUsuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(usrUsuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsrUsuario(usrUsuario.getUsrUser()) != null) {
                throw new PreexistingEntityException("UsrUsuario " + usrUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsrUsuario usrUsuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            usrUsuario = em.merge(usrUsuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usrUsuario.getUsrUser();
                if (findUsrUsuario(id) == null) {
                    throw new NonexistentEntityException("The usrUsuario with id " + id + " no longer exists.");
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
            UsrUsuario usrUsuario;
            try {
                usrUsuario = em.getReference(UsrUsuario.class, id);
                usrUsuario.getUsrUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usrUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(usrUsuario);
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

    public List<UsrUsuario> findUsrUsuarioEntities() {
        return findUsrUsuarioEntities(true, -1, -1);
    }

    public List<UsrUsuario> findUsrUsuarioEntities(int maxResults, int firstResult) {
        return findUsrUsuarioEntities(false, maxResults, firstResult);
    }

    private List<UsrUsuario> findUsrUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsrUsuario.class));
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

    public UsrUsuario findUsrUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsrUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsrUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsrUsuario> rt = cq.from(UsrUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
