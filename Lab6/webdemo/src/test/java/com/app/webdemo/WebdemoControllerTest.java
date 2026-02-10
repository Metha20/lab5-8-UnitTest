package com.app.webdemo;
import com.app.webdemo.controller.WebdemoController;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WebdemoController.class)
class WebdemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home_shouldReturnIndex() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"));
    }

    @Test
    void registrationPage_shouldReturnRegistrationView() throws Exception {
        mockMvc.perform(get("/registration"))
               .andExpect(status().isOk())
               .andExpect(view().name("registration"))
               .andExpect(model().attributeExists("registrationForm"));
    }
    @Test
void postRegister_shouldReturnSuccessView() throws Exception {
    mockMvc.perform(post("/register")
            .param("firstName", "Yanaphat")
            .param("lastName", "Mis")
            .param("country", "Thailand")
            .param("dob", "2000-01-01")
            .param("email", "yanaphat3110@gmail.com"))
        .andExpect(status().isOk())
        .andExpect(view().name("success"));
}

}
