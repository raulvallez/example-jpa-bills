/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controllers;

import jpa.entities.Client;
import jpa.entities.Factura;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jpa.entities.Linia;
import jpa.exceptions.ElementNoExists;

/**
 *
 * @author rvallez
 */
public class FacturaController {

    private EntityManagerFactory emf;

    public FacturaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
    
    public void addFactura(Factura factura) {
        EntityManager em = null;
        Query q = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void addFactura(String nom, Date data) {
        EntityManager em = null;
        Client client = null;
        Factura factura = null;
        Query q = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Client.findByNom", Client.class);
            query.setParameter("nom", nom);
            client = (Client) query.getSingleResult();
            factura = new Factura();
            factura.setClient(client);
            factura.setDate(data);
            em.persist(factura);
            em.getTransaction().commit();
            
            System.out.println(factura.getId());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
    }
    
    public void addFactura(Date data, String nom, List<Linia> linies) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Factura fact = new Factura();

            Client customer = new Client();
            customer.setNom(nom);

            fact.setClient(customer);
            fact.setLinies(linies);
            fact.setDate(new Date());

            em.persist(fact);
            em.getTransaction().commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Factura> getFactures() {
        EntityManager em = null;
        List<Factura> factures = null;
        try {
            em = getEntityManager();
            Query query = em.createQuery("SELECT f FROM Factura f");
            factures = query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return factures;
    }

    public List<Factura> getFacturesByClient(String nom) throws ElementNoExists {
        EntityManager em = null;
        List<Factura> factures = null;
        Query query = null;
        try {
            em = getEntityManager();
            query = em.createNamedQuery("Factura.findByClientNom",Factura.class);
            query.setParameter("nom", nom); 
            factures = query.getResultList();
        } catch(NoResultException ex){
            throw new ElementNoExists("El Client "+ nom +" no existeix per buscar les factures");
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return factures;
    }
}
