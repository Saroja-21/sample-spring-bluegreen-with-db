package pl.piomin.samples.spring.bluegreen.person.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class PersonControllerUnitTest {

    private final PersonController controller = new PersonController();

    @Test
    @DisplayName("hello() should return expected greeting message")
    void hello_ShouldReturnExpectedMessage() {
        String result = controller.hello();
        assertEquals("Hello from Person Service", result);
    }

    @Test
    @DisplayName("hello() should not return null")
    void hello_ShouldNotReturnNull() {
        String result = controller.hello();
        assertNotNull(result);
    }

    @Test
    @DisplayName("hello() should not return empty string")
    void hello_ShouldNotReturnEmptyString() {
        String result = controller.hello();
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("hello() should contain 'Person Service' in response")
    void hello_ShouldContainPersonService() {
        String result = controller.hello();
        assertTrue(result.contains("Person Service"));
    }

    @Test
    @DisplayName("hello() should start with 'Hello'")
    void hello_ShouldStartWithHello() {
        String result = controller.hello();
        assertTrue(result.startsWith("Hello"));
    }

    @Test
    @DisplayName("hello() should return consistent results on multiple calls")
    void hello_ShouldReturnConsistentResults() {
        String result1 = controller.hello();
        String result2 = controller.hello();
        String result3 = controller.hello();

        assertEquals(result1, result2);
        assertEquals(result2, result3);
    }
}
