package pl.piomin.samples.spring.bluegreen.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void helloEndpoint() {
        String response = restTemplate.getForObject("/hello", String.class);
        assertEquals("Hello from Person Service", response);
    }
}
