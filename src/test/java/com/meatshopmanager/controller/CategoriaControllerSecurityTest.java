package com.meatshopmanager.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void funcionarioDeveConseguirListarCategorias() throws Exception {
        mockMvc.perform(get("/api/categorias")
                        .with(user("funcionario").roles("FUNCIONARIO")))
                .andExpect(status().isOk());
    }
}