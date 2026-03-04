package pl.piomin.samples.spring.bluegreen.caller.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallerControllerUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CallerController controller;

    @Test
    void call_WhenSuccessful_ShouldReturnResponseBody() {
        String expectedBody = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":30}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(expectedBody, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        String result = controller.call();

        assertEquals(expectedBody, result);
        verify(restTemplate, times(1)).getForEntity("http://person:8080/persons/1", String.class);
    }

    @Test
    void call_WhenNotFound_ShouldReturnErrorMessage() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        String result = controller.call();

        assertEquals("Error: HTTP 404", result);
        verify(restTemplate, times(1)).getForEntity("http://person:8080/persons/1", String.class);
    }

    @Test
    void call_WhenServerError_ShouldReturnErrorMessage() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        String result = controller.call();

        assertEquals("Error: HTTP 500", result);
        verify(restTemplate, times(1)).getForEntity("http://person:8080/persons/1", String.class);
    }

    @Test
    void call_WhenServiceUnavailable_ShouldReturnErrorMessage() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        String result = controller.call();

        assertEquals("Error: HTTP 503", result);
        verify(restTemplate, times(1)).getForEntity("http://person:8080/persons/1", String.class);
    }

    @Test
    void call_WhenSuccessfulWithEmptyBody_ShouldReturnNull() {
        ResponseEntity<String> mockResponse = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponse);

        String result = controller.call();

        assertEquals(null, result);
        verify(restTemplate, times(1)).getForEntity("http://person:8080/persons/1", String.class);
    }
}
