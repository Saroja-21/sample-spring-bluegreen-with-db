package pl.piomin.samples.spring.bluegreen.person.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(PersonController.class)
class PersonControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /hello should return 200 OK")
    void getHello_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /hello should return expected message")
    void getHello_ShouldReturnExpectedMessage() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Person Service"));
    }

    @Test
    @DisplayName("GET /hello should return content type text/plain")
    void getHello_ShouldReturnTextPlain() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    @DisplayName("GET /hello should contain 'Person Service' in response")
    void getHello_ShouldContainPersonService() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Person Service")));
    }

    @Test
    @DisplayName("POST /hello should return 405 Method Not Allowed")
    void postHello_ShouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(post("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("PUT /hello should return 405 Method Not Allowed")
    void putHello_ShouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(put("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("DELETE /hello should return 405 Method Not Allowed")
    void deleteHello_ShouldReturnMethodNotAllowed() throws Exception {
        mockMvc.perform(delete("/hello"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("GET /nonexistent should return 404 Not Found")
    void getNonexistent_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/nonexistent"))
                .andExpect(status().isNotFound());
    }
}
