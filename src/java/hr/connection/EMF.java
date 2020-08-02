/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.connection;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author Mario
 */
public class EMF {
    private EntityManagerFactory connFactory;
    private static EMF emf = new EMF();
    private EMF(){
        connFactory = Persistence.createEntityManagerFactory("JPAPU");
    }
    public static EMF getInstance(){
        return emf;
    }
    public EntityManagerFactory getEntityManagerFactory(){
        return connFactory;
    }
}
