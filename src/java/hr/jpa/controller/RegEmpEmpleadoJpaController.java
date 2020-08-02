/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.jpa.controller;

import hr.jpa.controller.exceptions.IllegalOrphanException;
import hr.jpa.controller.exceptions.NonexistentEntityException;
import hr.jpa.controller.exceptions.PreexistingEntityException;
import hr.jpa.controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hr.jpa.entities.RegDepDepartamento;
import hr.jpa.entities.RegEmpEmpleado;
import hr.jpa.entities.RegEstEstadoEmpleado;
import hr.jpa.entities.RegPlanPlanilla;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mario
 */
public class RegEmpEmpleadoJpaController implements Serializable {

    public RegEmpEmpleadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegEmpEmpleado regEmpEmpleado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (regEmpEmpleado.getRegPlanPlanillaList() == null) {
            regEmpEmpleado.setRegPlanPlanillaList(new ArrayList<RegPlanPlanilla>());
        }
        if (regEmpEmpleado.getRegEmpEmpleadoList() == null) {
            regEmpEmpleado.setRegEmpEmpleadoList(new ArrayList<RegEmpEmpleado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegDepDepartamento depNombre = regEmpEmpleado.getDepNombre();
            if (depNombre != null) {
                depNombre = em.getReference(depNombre.getClass(), depNombre.getDepNombre());
                regEmpEmpleado.setDepNombre(depNombre);
            }
            RegEmpEmpleado empIdJefe = regEmpEmpleado.getEmpIdJefe();
            if (empIdJefe != null) {
                empIdJefe = em.getReference(empIdJefe.getClass(), empIdJefe.getEmpId());
                regEmpEmpleado.setEmpIdJefe(empIdJefe);
            }
            RegEstEstadoEmpleado estId = regEmpEmpleado.getEstId();
            if (estId != null) {
                estId = em.getReference(estId.getClass(), estId.getEstId());
                regEmpEmpleado.setEstId(estId);
            }
            List<RegPlanPlanilla> attachedRegPlanPlanillaList = new ArrayList<RegPlanPlanilla>();
            for (RegPlanPlanilla regPlanPlanillaListRegPlanPlanillaToAttach : regEmpEmpleado.getRegPlanPlanillaList()) {
                regPlanPlanillaListRegPlanPlanillaToAttach = em.getReference(regPlanPlanillaListRegPlanPlanillaToAttach.getClass(), regPlanPlanillaListRegPlanPlanillaToAttach.getRegPlanPlanillaPK());
                attachedRegPlanPlanillaList.add(regPlanPlanillaListRegPlanPlanillaToAttach);
            }
            regEmpEmpleado.setRegPlanPlanillaList(attachedRegPlanPlanillaList);
            List<RegEmpEmpleado> attachedRegEmpEmpleadoList = new ArrayList<RegEmpEmpleado>();
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleadoToAttach : regEmpEmpleado.getRegEmpEmpleadoList()) {
                regEmpEmpleadoListRegEmpEmpleadoToAttach = em.getReference(regEmpEmpleadoListRegEmpEmpleadoToAttach.getClass(), regEmpEmpleadoListRegEmpEmpleadoToAttach.getEmpId());
                attachedRegEmpEmpleadoList.add(regEmpEmpleadoListRegEmpEmpleadoToAttach);
            }
            regEmpEmpleado.setRegEmpEmpleadoList(attachedRegEmpEmpleadoList);
            em.persist(regEmpEmpleado);
            if (depNombre != null) {
                depNombre.getRegEmpEmpleadoList().add(regEmpEmpleado);
                depNombre = em.merge(depNombre);
            }
            if (empIdJefe != null) {
                empIdJefe.getRegEmpEmpleadoList().add(regEmpEmpleado);
                empIdJefe = em.merge(empIdJefe);
            }
            if (estId != null) {
                estId.getRegEmpEmpleadoList().add(regEmpEmpleado);
                estId = em.merge(estId);
            }
            for (RegPlanPlanilla regPlanPlanillaListRegPlanPlanilla : regEmpEmpleado.getRegPlanPlanillaList()) {
                RegEmpEmpleado oldRegEmpEmpleadoOfRegPlanPlanillaListRegPlanPlanilla = regPlanPlanillaListRegPlanPlanilla.getRegEmpEmpleado();
                regPlanPlanillaListRegPlanPlanilla.setRegEmpEmpleado(regEmpEmpleado);
                regPlanPlanillaListRegPlanPlanilla = em.merge(regPlanPlanillaListRegPlanPlanilla);
                if (oldRegEmpEmpleadoOfRegPlanPlanillaListRegPlanPlanilla != null) {
                    oldRegEmpEmpleadoOfRegPlanPlanillaListRegPlanPlanilla.getRegPlanPlanillaList().remove(regPlanPlanillaListRegPlanPlanilla);
                    oldRegEmpEmpleadoOfRegPlanPlanillaListRegPlanPlanilla = em.merge(oldRegEmpEmpleadoOfRegPlanPlanillaListRegPlanPlanilla);
                }
            }
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleado : regEmpEmpleado.getRegEmpEmpleadoList()) {
                RegEmpEmpleado oldEmpIdJefeOfRegEmpEmpleadoListRegEmpEmpleado = regEmpEmpleadoListRegEmpEmpleado.getEmpIdJefe();
                regEmpEmpleadoListRegEmpEmpleado.setEmpIdJefe(regEmpEmpleado);
                regEmpEmpleadoListRegEmpEmpleado = em.merge(regEmpEmpleadoListRegEmpEmpleado);
                if (oldEmpIdJefeOfRegEmpEmpleadoListRegEmpEmpleado != null) {
                    oldEmpIdJefeOfRegEmpEmpleadoListRegEmpEmpleado.getRegEmpEmpleadoList().remove(regEmpEmpleadoListRegEmpEmpleado);
                    oldEmpIdJefeOfRegEmpEmpleadoListRegEmpEmpleado = em.merge(oldEmpIdJefeOfRegEmpEmpleadoListRegEmpEmpleado);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegEmpEmpleado(regEmpEmpleado.getEmpId()) != null) {
                throw new PreexistingEntityException("RegEmpEmpleado " + regEmpEmpleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegEmpEmpleado regEmpEmpleado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegEmpEmpleado persistentRegEmpEmpleado = em.find(RegEmpEmpleado.class, regEmpEmpleado.getEmpId());
            RegDepDepartamento depNombreOld = persistentRegEmpEmpleado.getDepNombre();
            RegDepDepartamento depNombreNew = regEmpEmpleado.getDepNombre();
            RegEmpEmpleado empIdJefeOld = persistentRegEmpEmpleado.getEmpIdJefe();
            RegEmpEmpleado empIdJefeNew = regEmpEmpleado.getEmpIdJefe();
            RegEstEstadoEmpleado estIdOld = persistentRegEmpEmpleado.getEstId();
            RegEstEstadoEmpleado estIdNew = regEmpEmpleado.getEstId();
            List<RegPlanPlanilla> regPlanPlanillaListOld = persistentRegEmpEmpleado.getRegPlanPlanillaList();
            List<RegPlanPlanilla> regPlanPlanillaListNew = regEmpEmpleado.getRegPlanPlanillaList();
            List<RegEmpEmpleado> regEmpEmpleadoListOld = persistentRegEmpEmpleado.getRegEmpEmpleadoList();
            List<RegEmpEmpleado> regEmpEmpleadoListNew = regEmpEmpleado.getRegEmpEmpleadoList();
            List<String> illegalOrphanMessages = null;
            for (RegPlanPlanilla regPlanPlanillaListOldRegPlanPlanilla : regPlanPlanillaListOld) {
                if (!regPlanPlanillaListNew.contains(regPlanPlanillaListOldRegPlanPlanilla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegPlanPlanilla " + regPlanPlanillaListOldRegPlanPlanilla + " since its regEmpEmpleado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (depNombreNew != null) {
                depNombreNew = em.getReference(depNombreNew.getClass(), depNombreNew.getDepNombre());
                regEmpEmpleado.setDepNombre(depNombreNew);
            }
            if (empIdJefeNew != null) {
                empIdJefeNew = em.getReference(empIdJefeNew.getClass(), empIdJefeNew.getEmpId());
                regEmpEmpleado.setEmpIdJefe(empIdJefeNew);
            }
            if (estIdNew != null) {
                estIdNew = em.getReference(estIdNew.getClass(), estIdNew.getEstId());
                regEmpEmpleado.setEstId(estIdNew);
            }
            List<RegPlanPlanilla> attachedRegPlanPlanillaListNew = new ArrayList<RegPlanPlanilla>();
            for (RegPlanPlanilla regPlanPlanillaListNewRegPlanPlanillaToAttach : regPlanPlanillaListNew) {
                regPlanPlanillaListNewRegPlanPlanillaToAttach = em.getReference(regPlanPlanillaListNewRegPlanPlanillaToAttach.getClass(), regPlanPlanillaListNewRegPlanPlanillaToAttach.getRegPlanPlanillaPK());
                attachedRegPlanPlanillaListNew.add(regPlanPlanillaListNewRegPlanPlanillaToAttach);
            }
            regPlanPlanillaListNew = attachedRegPlanPlanillaListNew;
            regEmpEmpleado.setRegPlanPlanillaList(regPlanPlanillaListNew);
            List<RegEmpEmpleado> attachedRegEmpEmpleadoListNew = new ArrayList<RegEmpEmpleado>();
            for (RegEmpEmpleado regEmpEmpleadoListNewRegEmpEmpleadoToAttach : regEmpEmpleadoListNew) {
                regEmpEmpleadoListNewRegEmpEmpleadoToAttach = em.getReference(regEmpEmpleadoListNewRegEmpEmpleadoToAttach.getClass(), regEmpEmpleadoListNewRegEmpEmpleadoToAttach.getEmpId());
                attachedRegEmpEmpleadoListNew.add(regEmpEmpleadoListNewRegEmpEmpleadoToAttach);
            }
            regEmpEmpleadoListNew = attachedRegEmpEmpleadoListNew;
            regEmpEmpleado.setRegEmpEmpleadoList(regEmpEmpleadoListNew);
            regEmpEmpleado = em.merge(regEmpEmpleado);
            if (depNombreOld != null && !depNombreOld.equals(depNombreNew)) {
                depNombreOld.getRegEmpEmpleadoList().remove(regEmpEmpleado);
                depNombreOld = em.merge(depNombreOld);
            }
            if (depNombreNew != null && !depNombreNew.equals(depNombreOld)) {
                depNombreNew.getRegEmpEmpleadoList().add(regEmpEmpleado);
                depNombreNew = em.merge(depNombreNew);
            }
            if (empIdJefeOld != null && !empIdJefeOld.equals(empIdJefeNew)) {
                empIdJefeOld.getRegEmpEmpleadoList().remove(regEmpEmpleado);
                empIdJefeOld = em.merge(empIdJefeOld);
            }
            if (empIdJefeNew != null && !empIdJefeNew.equals(empIdJefeOld)) {
                empIdJefeNew.getRegEmpEmpleadoList().add(regEmpEmpleado);
                empIdJefeNew = em.merge(empIdJefeNew);
            }
            if (estIdOld != null && !estIdOld.equals(estIdNew)) {
                estIdOld.getRegEmpEmpleadoList().remove(regEmpEmpleado);
                estIdOld = em.merge(estIdOld);
            }
            if (estIdNew != null && !estIdNew.equals(estIdOld)) {
                estIdNew.getRegEmpEmpleadoList().add(regEmpEmpleado);
                estIdNew = em.merge(estIdNew);
            }
            for (RegPlanPlanilla regPlanPlanillaListNewRegPlanPlanilla : regPlanPlanillaListNew) {
                if (!regPlanPlanillaListOld.contains(regPlanPlanillaListNewRegPlanPlanilla)) {
                    RegEmpEmpleado oldRegEmpEmpleadoOfRegPlanPlanillaListNewRegPlanPlanilla = regPlanPlanillaListNewRegPlanPlanilla.getRegEmpEmpleado();
                    regPlanPlanillaListNewRegPlanPlanilla.setRegEmpEmpleado(regEmpEmpleado);
                    regPlanPlanillaListNewRegPlanPlanilla = em.merge(regPlanPlanillaListNewRegPlanPlanilla);
                    if (oldRegEmpEmpleadoOfRegPlanPlanillaListNewRegPlanPlanilla != null && !oldRegEmpEmpleadoOfRegPlanPlanillaListNewRegPlanPlanilla.equals(regEmpEmpleado)) {
                        oldRegEmpEmpleadoOfRegPlanPlanillaListNewRegPlanPlanilla.getRegPlanPlanillaList().remove(regPlanPlanillaListNewRegPlanPlanilla);
                        oldRegEmpEmpleadoOfRegPlanPlanillaListNewRegPlanPlanilla = em.merge(oldRegEmpEmpleadoOfRegPlanPlanillaListNewRegPlanPlanilla);
                    }
                }
            }
            for (RegEmpEmpleado regEmpEmpleadoListOldRegEmpEmpleado : regEmpEmpleadoListOld) {
                if (!regEmpEmpleadoListNew.contains(regEmpEmpleadoListOldRegEmpEmpleado)) {
                    regEmpEmpleadoListOldRegEmpEmpleado.setEmpIdJefe(null);
                    regEmpEmpleadoListOldRegEmpEmpleado = em.merge(regEmpEmpleadoListOldRegEmpEmpleado);
                }
            }
            for (RegEmpEmpleado regEmpEmpleadoListNewRegEmpEmpleado : regEmpEmpleadoListNew) {
                if (!regEmpEmpleadoListOld.contains(regEmpEmpleadoListNewRegEmpEmpleado)) {
                    RegEmpEmpleado oldEmpIdJefeOfRegEmpEmpleadoListNewRegEmpEmpleado = regEmpEmpleadoListNewRegEmpEmpleado.getEmpIdJefe();
                    regEmpEmpleadoListNewRegEmpEmpleado.setEmpIdJefe(regEmpEmpleado);
                    regEmpEmpleadoListNewRegEmpEmpleado = em.merge(regEmpEmpleadoListNewRegEmpEmpleado);
                    if (oldEmpIdJefeOfRegEmpEmpleadoListNewRegEmpEmpleado != null && !oldEmpIdJefeOfRegEmpEmpleadoListNewRegEmpEmpleado.equals(regEmpEmpleado)) {
                        oldEmpIdJefeOfRegEmpEmpleadoListNewRegEmpEmpleado.getRegEmpEmpleadoList().remove(regEmpEmpleadoListNewRegEmpEmpleado);
                        oldEmpIdJefeOfRegEmpEmpleadoListNewRegEmpEmpleado = em.merge(oldEmpIdJefeOfRegEmpEmpleadoListNewRegEmpEmpleado);
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
                Integer id = regEmpEmpleado.getEmpId();
                if (findRegEmpEmpleado(id) == null) {
                    throw new NonexistentEntityException("The regEmpEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RegEmpEmpleado regEmpEmpleado;
            try {
                regEmpEmpleado = em.getReference(RegEmpEmpleado.class, id);
                regEmpEmpleado.getEmpId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The regEmpEmpleado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RegPlanPlanilla> regPlanPlanillaListOrphanCheck = regEmpEmpleado.getRegPlanPlanillaList();
            for (RegPlanPlanilla regPlanPlanillaListOrphanCheckRegPlanPlanilla : regPlanPlanillaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RegEmpEmpleado (" + regEmpEmpleado + ") cannot be destroyed since the RegPlanPlanilla " + regPlanPlanillaListOrphanCheckRegPlanPlanilla + " in its regPlanPlanillaList field has a non-nullable regEmpEmpleado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RegDepDepartamento depNombre = regEmpEmpleado.getDepNombre();
            if (depNombre != null) {
                depNombre.getRegEmpEmpleadoList().remove(regEmpEmpleado);
                depNombre = em.merge(depNombre);
            }
            RegEmpEmpleado empIdJefe = regEmpEmpleado.getEmpIdJefe();
            if (empIdJefe != null) {
                empIdJefe.getRegEmpEmpleadoList().remove(regEmpEmpleado);
                empIdJefe = em.merge(empIdJefe);
            }
            RegEstEstadoEmpleado estId = regEmpEmpleado.getEstId();
            if (estId != null) {
                estId.getRegEmpEmpleadoList().remove(regEmpEmpleado);
                estId = em.merge(estId);
            }
            List<RegEmpEmpleado> regEmpEmpleadoList = regEmpEmpleado.getRegEmpEmpleadoList();
            for (RegEmpEmpleado regEmpEmpleadoListRegEmpEmpleado : regEmpEmpleadoList) {
                regEmpEmpleadoListRegEmpEmpleado.setEmpIdJefe(null);
                regEmpEmpleadoListRegEmpEmpleado = em.merge(regEmpEmpleadoListRegEmpEmpleado);
            }
            em.remove(regEmpEmpleado);
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

    public List<RegEmpEmpleado> findRegEmpEmpleadoEntities() {
        return findRegEmpEmpleadoEntities(true, -1, -1);
    }

    public List<RegEmpEmpleado> findRegEmpEmpleadoEntities(int maxResults, int firstResult) {
        return findRegEmpEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<RegEmpEmpleado> findRegEmpEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegEmpEmpleado.class));
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

    public RegEmpEmpleado findRegEmpEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegEmpEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegEmpEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegEmpEmpleado> rt = cq.from(RegEmpEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
