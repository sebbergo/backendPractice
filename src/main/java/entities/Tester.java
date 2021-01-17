/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.AddressDTO;
import dto.UserDTO;
import dto.UserPlanetDTO;
import facades.UserFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author sebas
 */
public class Tester {
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        UserFacade uf = UserFacade.getUserFacade(emf);

        User u1 = new User("Sebastian", "testuser1");
        User u2 = new User("Sumit", "testuser2");
        
        Address a1 = new Address("Christian X's Allé");
        Address a2 = new Address("Taastrup Gade");
        
        Fee f1 = new Fee("100");
        Fee f2 = new Fee("200");
        
        SwimStyle ss1 = new SwimStyle("crawl");
        SwimStyle ss2 = new SwimStyle("butterfly");
        
        u1.setAddress(a1);
        u2.setAddress(a2);
        
        u1.addFees(f1);
        u2.addFees(f2);
        
        u1.addSwimStyle(ss1);
        u2.addSwimStyle(ss2);
        
        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role userRole2 = new Role("user");
        
        u1.addRole(userRole);
        u2.addRole(userRole2);
        
        em.persist(userRole);
        em.persist(u1);
        em.persist(u2);
        em.getTransaction().commit();
        
    }
}
