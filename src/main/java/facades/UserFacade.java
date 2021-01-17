package facades;

import dto.PlanetDTO;
import dto.UserDTO;
import dto.UserPlanetDTO;
import entities.Address;
import entities.Fee;
import entities.Joke;
import entities.Planet;
import entities.Role;
import entities.SwimStyle;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public void storeJoke(String joke) {
        EntityManager em = emf.createEntityManager();
        try {
            Joke jokeToStore = new Joke(joke);

            em.getTransaction().begin();
            em.persist(jokeToStore);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public UserDTO userByStreet(String street) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery query = em.createQuery("SELECT u FROM User u WHERE u.address.street = :street", Address.class);
            query.setParameter("street", street);

            User u = (User) query.getSingleResult();

            return new UserDTO(u);
        } finally {
            em.close();
        }
    }

    /*
    public UserDTO postUser()
     */
    
    public UserDTO editUser(UserDTO userDTO){
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            
            User user = em.find(User.class, userDTO.userName);
            
            user.setUserName(userDTO.userName);
            user.getAddress().setStreet(userDTO.street);
            
            em.merge(user);
            
            em.getTransaction().commit();
            
            return new UserDTO(user);
        }finally{
            em.close();
        }
    }
    
    public UserDTO deleteUser(UserDTO userDTO){
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            
            User user = em.find(User.class, userDTO.userName);
            
            for (Fee fee : user.getFees()) {
                em.remove(fee);
            }

            for (Role role : user.getRoleList()) {
                em.remove(role);
            }
            
            for (SwimStyle swimStyle : user.getSwimStyles()) {
                em.remove(swimStyle);
            }
            
            em.remove(user);
            
            em.getTransaction().commit();
            
            return new UserDTO(user);
        }finally{
            em.close();
        }
    }
    
    public UserPlanetDTO addPlanetToUser(UserPlanetDTO userPlanetDTO){
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            
            User user = em.find(User.class, userPlanetDTO.userName);
            
            Planet planet = new Planet(userPlanetDTO.planetName, userPlanetDTO.planetClimate,
            userPlanetDTO.planetTerrain, userPlanetDTO.planetPopulation);
            
            user.setPlanet(planet);
            
            em.merge(user);
            
            em.getTransaction().commit();
            
            return userPlanetDTO;
            
        }finally{
            em.close();
        }
    }
}
