package com.ctf.sample.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = GreetingController.class,
        properties = {
                "greeting=Test Hello World",
                "comment=This is a comment",
                "hostname=localhost"
        })
public class GreetingControllerTest {

    private static final String EXPECTED_GREETING = "Test Hello World";

    private static final String EXPECTED_COMMENT = "This is a comment";

    private static final String EXPECTED_HOSTNAME = "localhost";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Successful response from GreetingController")
    public void successfulResponse() throws Exception {
        this.mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.greeting").value(EXPECTED_GREETING))
                .andExpect(jsonPath("$.comment").value(EXPECTED_COMMENT))
                .andExpect(jsonPath("$.hostname").value(EXPECTED_HOSTNAME));
    }

}
