package pl.piomin.samples.spring.bluegreen.person.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonControllerUnitTest {

    private final PersonController controller = new PersonController();

    @Test
    void hello_ShouldReturnExpectedMessage() {
        String result = controller.hello();
        assertEquals("Hello from Person Service", result);
    }
}
