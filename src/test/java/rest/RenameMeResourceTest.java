package rest;

import com.nimbusds.jose.shaded.json.JSONObject;
import dto.UserPlanetDTO;
import entities.Address;
import entities.Fee;
import entities.RenameMe;
import entities.Role;
import entities.SwimStyle;
import entities.User;
import facades.UserFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class RenameMeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static RenameMe r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static User user1;
    private static User user2;
    private static Role userRole;
    private static Role adminRole;
    private static User admin;
    private static User both;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        
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
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/info").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/info/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello anonymous"));
    }

    @Disabled
    @Test
    public void testParrallel() throws Exception {
        given()
                .contentType("application/json")
                .get("/info/sw").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("peopleName", equalTo("Luke Skywalker"))
                .body("planetName", equalTo("Yavin IV"))
                .body("speciesName", equalTo("Ewok"))
                .body("starshipName", equalTo("Star Destroyer"))
                .body("vehicleName", equalTo("Sand Crawler"));

    }

    @Disabled
    @Test
    public void testCached() throws Exception {
        given()
                .contentType("application/json")
                .get("/info/cached").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("peopleName", equalTo("Luke Skywalker"))
                .body("planetName", equalTo("Yavin IV"))
                .body("speciesName", equalTo("Ewok"))
                .body("starshipName", equalTo("Star Destroyer"))
                .body("vehicleName", equalTo("Sand Crawler"));
    }

    @Test
    public void testAddPlanetToUser() throws Exception {
        JSONObject request = new JSONObject();
        request.put("userName", "Sebastian");
        request.put("planetName", "Tattooine");
        request.put("planetClimate", "arid");
        request.put("planetTerrain", "desert");
        request.put("planetPopulation", "200000");

        given()
                .contentType("application/json")
                .body(request.toJSONString())
                .when()
                .put("/info/addPlanetToUser")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());

    }
}
