package facades;

import dto.PlanetDTO;
import dto.UserDTO;
import dto.UserPlanetDTO;
import entities.Address;
import entities.Fee;
import entities.Planet;
import utils.EMF_Creator;
import entities.Role;
import entities.SwimStyle;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static User user1;
    private static User user2;
    private static User admin;
    private static User both;
    private static Role userRole;
    private static Role adminRole;
    private static UserDTO userDTO;
    private static User user;
    private static Address address;
    private static PlanetDTO planetDTO;
    private static Planet planet;
    private static UserPlanetDTO userPlanetDTO;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
        EntityManager em = emf.createEntityManager();

        user1 = new User("Sebastian", "testuser1");
        user2 = new User("Sumit", "testuser2");

        Address a1 = new Address("Christian X's Allé");
        Address a2 = new Address("Taastrup Gade");

        Fee f1 = new Fee("100");
        Fee f2 = new Fee("200");

        SwimStyle ss1 = new SwimStyle("crawl");
        SwimStyle ss2 = new SwimStyle("butterfly");

        user1.setAddress(a1);
        user2.setAddress(a2);

        user1.addFees(f1);
        user2.addFees(f2);

        user1.addSwimStyle(ss1);
        user2.addSwimStyle(ss2);

        userRole = new Role("user");
        adminRole = new Role("admin");

        user1.addRole(userRole);
        user2.addRole(userRole);

        admin = new User("admin", "test");
        admin.addRole(adminRole);

        both = new User("user_admin", "test");
        both.addRole(userRole);
        both.addRole(adminRole);

        try {
            em.getTransaction().begin();

            //Delete existing users and roles to get a "fresh" database
            em.createNativeQuery("delete from FEE").executeUpdate();
            em.createNativeQuery("delete from user_roles").executeUpdate();
            em.createNativeQuery("delete from roles").executeUpdate();
            em.createNativeQuery("delete from SWIMSTYLE_users").executeUpdate();
            em.createNativeQuery("delete from SWIMSTYLE").executeUpdate();
            em.createNativeQuery("delete from users").executeUpdate();

            //Persisting new data
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user1);
            em.persist(user2);
            em.persist(admin);
            em.persist(both);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testVerifyUser() throws AuthenticationException {
        user = facade.getVeryfiedUser("Sebastian", "testuser1");
        assertEquals("admin", admin.getUserName());
    }

    @Test
    public void testUserByStreet() {
        userDTO = facade.userByStreet("Taastrup Gade");
        assertEquals("Taastrup Gade", userDTO.street);
        assertEquals("200", userDTO.fees.get(0).price);
    }

    @Test
    public void testEditUser() {
        user = new User("Sebastian", "testuser1");
        address = new Address("Kølen");
        user.setAddress(address);

        userDTO = new UserDTO(user);

        UserDTO userDTOResult = facade.editUser(userDTO);

        assertEquals(userDTO.street, userDTOResult.street);
    }

    @Test
    public void testDeleteUser() {
        user = new User("Sebastian", "testuser1");
        userDTO = new UserDTO(user);

        UserDTO userDTOResult = facade.deleteUser(userDTO);

        assertEquals(userDTO.userName, userDTOResult.userName);
    }

    @Test
    public void testAddPlanet() {
        userPlanetDTO = new UserPlanetDTO(
                "Sebastian",
                "Tattoine",
                "arid",
                "desert",
                "200000");

        UserPlanetDTO userPlanetDTOResult = facade.addPlanetToUser(userPlanetDTO);

        assertEquals(userPlanetDTOResult.userName, userPlanetDTO.userName);
        assertEquals(userPlanetDTOResult.planetName, userPlanetDTO.planetName);
        assertEquals(userPlanetDTOResult.planetClimate, userPlanetDTO.planetClimate);
        assertEquals(userPlanetDTOResult.planetTerrain, userPlanetDTO.planetTerrain);
        assertEquals(userPlanetDTOResult.planetPopulation, userPlanetDTO.planetPopulation);
    }

}
