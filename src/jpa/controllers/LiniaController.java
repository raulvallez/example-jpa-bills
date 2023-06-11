/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controllers;

import jpa.entities.Factura;
import jpa.entities.Linia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jpa.exceptions.ElementNoExists;

/**
 *
 * @author rvallez
 */
public class LiniaController {
    
    private EntityManagerFactory emf;

    public LiniaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public LiniaController() {
        
    }
    
    public EntityManager getEntityManager(){        
        return emf.createEntityManager();
    }

    public List<Linia> getLinies(long id){
        List<Linia> linies = null;
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            Query q = em.createQuery("SELECT l FROM Linia l WHERE l.factura.id = :id");
            q.setParameter("id", id);
            linies = q.getResultList();
        }finally{
            if(em != null){
                em.close();
            }   
        }
        
        return linies;
    }

    public void modifyLinia(long id, 
                            String product,
                            double preu, 
                            double quantitat)
    {
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();

            Linia linia = em.getReference(Linia.class, id); // Al find: Linia linia = (Linia) em.find(Linia.class, id);
            linia.setProducte(product);
            linia.setPreu(preu);
            linia.setQuantitat(quantitat);
            
            em.merge(linia);

//            q.setParameter("producte",product);
//            Query q = em.createQuery("UPDATE Linia l SET l.producte = '" + product + "' ,"
//                                                     + "l.preu = " + preu 
//                                                     + ", l.quantitat = " + quantitat 
//                                 +"  WHERE l.id =" + id);
//            q.executeUpdate();
            em.getTransaction().commit();
        }finally{
            if(em != null){
                em.close();
            }   
        }
        
    }

    public void addLinia(long id, 
                        String producte, 
                        double preu, 
                        double quantitat) throws ElementNoExists
    {
        List<Linia> linies = null;
        EntityManager em = null;
        
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Factura.findById",Factura.class);
            query.setParameter("id", id);
            Factura factura = (Factura) query.getSingleResult();
            Linia linia = new Linia(producte, preu, quantitat);
            linia.setFactura(factura);
            factura.getLinies().add(linia);
            em.merge(factura);
            em.getTransaction().commit();         
        }catch(NoResultException ex) {
            throw new ElementNoExists("No existeix la Factura a la Base de dades");
        }finally{
            if(em != null){
                em.close();
            }   
        }
    }
    
    
    
//    Test(expected=IndexOutOfBoundsException.class)

//    public void testIndexOutOfBoundsException() {
//        ArrayList<String> emptyList = new ArrayList();
//   
//
//        
//        Object o = emptyList.get(6);
//        
//        
//        String [] palabras = null
//                
//        palabras[0];        
//    }
//    
//    
//    
    
}
