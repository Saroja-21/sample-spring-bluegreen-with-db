package pl.piomin.samples.spring.bluegreen.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.samples.spring.bluegreen.person.controller.PersonController;
import pl.piomin.samples.spring.bluegreen.person.repository.PersonRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.jpa.hibernate.ddl-auto=create"})
@Testcontainers
class PersonServiceSmokeTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.1")
            .withExposedPorts(5432);

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertNotNull(personController, "PersonController should be loaded");
        assertNotNull(personRepository, "PersonRepository should be loaded");
    }

    @Test
    void actuatorHealthEndpoint_ShouldReturnUp() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("UP"));
    }

    @Test
    void personsEndpoint_ShouldBeAccessible() {
        ResponseEntity<String> response = restTemplate.getForEntity("/persons", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void databaseConnection_ShouldBeWorking() {
        assertTrue(postgres.isRunning(), "PostgreSQL container should be running");

        long count = personRepository.count();
        assertTrue(count >= 0, "Should be able to query the database");
    }
}
