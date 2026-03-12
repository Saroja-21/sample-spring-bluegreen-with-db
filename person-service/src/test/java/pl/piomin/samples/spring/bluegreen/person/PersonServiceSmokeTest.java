package pl.piomin.samples.spring.bluegreen.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonServiceSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Application should start successfully")
    void applicationShouldStart() {
        assertNotNull(restTemplate);
    }

    @Test
    @DisplayName("Hello endpoint should be accessible")
    void helloEndpointShouldBeAccessible() {
        ResponseEntity<String> response = restTemplate.getForEntity("/hello", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Hello endpoint should return expected message")
    void helloEndpointShouldReturnExpectedMessage() {
        String response = restTemplate.getForObject("/hello", String.class);
        assertEquals("Hello from Person Service", response);
    }

    @Test
    @DisplayName("Hello endpoint response should not be null")
    void helloEndpointResponseShouldNotBeNull() {
        ResponseEntity<String> response = restTemplate.getForEntity("/hello", String.class);
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Actuator health endpoint should be accessible")
    void actuatorHealthShouldBeAccessible() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Actuator health should return UP status")
    void actuatorHealthShouldReturnUpStatus() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("UP"));
    }

    @Test
    @DisplayName("Server should be running on expected port")
    void serverShouldBeRunningOnExpectedPort() {
        assertTrue(port > 0);
        String url = "http://localhost:" + port + "/hello";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Non-existent endpoint should return 404")
    void nonExistentEndpointShouldReturn404() {
        ResponseEntity<String> response = restTemplate.getForEntity("/nonexistent", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
