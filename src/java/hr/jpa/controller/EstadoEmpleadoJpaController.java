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
import hr.jpa.entity.EstadoEmpleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mario
 */
public class EstadoEmpleadoJpaController implements Serializable {

    public EstadoEmpleadoJpaController() {
        EntityManagerFactory emfac = EMF.getInstance().getEntityManagerFactory();
        this.emf = emfac;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoEmpleado estadoEmpleado) throws PreexistingEntityException, Exception {
        if (estadoEmpleado.getEmpleadoList() == null) {
            estadoEmpleado.setEmpleadoList(new ArrayList<Empleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : estadoEmpleado.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmpId());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            estadoEmpleado.setEmpleadoList(attachedEmpleadoList);
            em.persist(estadoEmpleado);
            for (Empleado empleadoListEmpleado : estadoEmpleado.getEmpleadoList()) {
                EstadoEmpleado oldEstIdOfEmpleadoListEmpleado = empleadoListEmpleado.getEstId();
                empleadoListEmpleado.setEstId(estadoEmpleado);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldEstIdOfEmpleadoListEmpleado != null) {
                    oldEstIdOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldEstIdOfEmpleadoListEmpleado = em.merge(oldEstIdOfEmpleadoListEmpleado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoEmpleado(estadoEmpleado.getEstId()) != null) {
                throw new PreexistingEntityException("EstadoEmpleado " + estadoEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoEmpleado estadoEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoEmpleado persistentEstadoEmpleado = em.find(EstadoEmpleado.class, estadoEmpleado.getEstId());
            List<Empleado> empleadoListOld = persistentEstadoEmpleado.getEmpleadoList();
            List<Empleado> empleadoListNew = estadoEmpleado.getEmpleadoList();
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmpId());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            estadoEmpleado.setEmpleadoList(empleadoListNew);
            estadoEmpleado = em.merge(estadoEmpleado);
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    empleadoListOldEmpleado.setEstId(null);
                    empleadoListOldEmpleado = em.merge(empleadoListOldEmpleado);
                }
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    EstadoEmpleado oldEstIdOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getEstId();
                    empleadoListNewEmpleado.setEstId(estadoEmpleado);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldEstIdOfEmpleadoListNewEmpleado != null && !oldEstIdOfEmpleadoListNewEmpleado.equals(estadoEmpleado)) {
                        oldEstIdOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldEstIdOfEmpleadoListNewEmpleado = em.merge(oldEstIdOfEmpleadoListNewEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadoEmpleado.getEstId();
                if (findEstadoEmpleado(id) == null) {
                    throw new NonexistentEntityException("The estadoEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoEmpleado estadoEmpleado;
            try {
                estadoEmpleado = em.getReference(EstadoEmpleado.class, id);
                estadoEmpleado.getEstId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoEmpleado with id " + id + " no longer exists.", enfe);
            }
            List<Empleado> empleadoList = estadoEmpleado.getEmpleadoList();
            for (Empleado empleadoListEmpleado : empleadoList) {
                empleadoListEmpleado.setEstId(null);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
            }
            em.remove(estadoEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoEmpleado> findEstadoEmpleadoEntities() {
        return findEstadoEmpleadoEntities(true, -1, -1);
    }

    public List<EstadoEmpleado> findEstadoEmpleadoEntities(int maxResults, int firstResult) {
        return findEstadoEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<EstadoEmpleado> findEstadoEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoEmpleado.class));
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

    public EstadoEmpleado findEstadoEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoEmpleado> rt = cq.from(EstadoEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
