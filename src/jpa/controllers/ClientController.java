/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controllers;

import jpa.entities.Client;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import jpa.exceptions.ElementNoExists;

/**
 *
 * @author rvallez
 */
public class ClientController {

    private EntityManagerFactory emf;

    public ClientController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        EntityManager em = emf.createEntityManager();
        return em;
    }
    
    public void addClient(Client client){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
        } catch (EntityExistsException ex) {
            System.out.println("El client ja existeix");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Client> getClients() {
        EntityManager em = null;
        List<Client> clients;
        try {
            em = getEntityManager();
            Query q = em.createQuery("SELECT c FROM Client c");
            clients = q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return clients;
    }
    
    public Client getClientByName(String nom) throws ElementNoExists {
        EntityManager em = null;
        Client c = null;
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Query query = em.createNamedQuery("Client.findByNom", Client.class);
            query.setParameter("nom", nom);
            try {
                c = (Client) query.getSingleResult();
            } catch (NoResultException ex) {
                throw new ElementNoExists();
            }  
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
               
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return c;
    }
    
    
    public void deleteClient(String nom) throws ElementNoExists {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client c = null;

            Query query = em.createNamedQuery("Client.findByNom", Client.class);
            query.setParameter("nom", nom);
            try {
                c = (Client) query.getSingleResult();
                em.remove(c);
                em.getTransaction().commit();
                System.out.println("Eliminat");

            } catch (NoResultException ex) {
                throw new ElementNoExists();
            } 
 
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
