package com.deloitte.ads.mariosy.controller;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.service.MarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MariosController.class)
public class MariosControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    MarioService marioService;

    @Test
    void addMariosTest() throws Exception {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User receiver1 = new User("Bartosz", "bartek@gmail.com");
        User receiver2 = new User("Kamil", "kamil@gmail.com");

        Set<UUID> receiversExternalId = Set.of(receiver1.getExternalId(), receiver2.getExternalId());
        MariosDTO mariosDTO = new MariosDTO("Mario", "Good job!", author.getExternalId(), receiversExternalId, "Greeting");
        Marios marios = new Marios("Mario", "Good job!", author, "Greeting");

        // when then
        when(marioService.addMarios(any(MariosDTO.class))).thenReturn(marios);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/marios/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mariosDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.externalId").value(marios.getExternalId().toString()))
                .andExpect(jsonPath("$.characterName").value("Mario"))
                .andExpect(jsonPath("$.comment").value("Good job!"));
    }

    @Test
    void deleteMariosTest() throws Exception {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author, "Greeting");

        // when then
        mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/marios/delete/{mariosExternalId}&{authorExternalId}", marios.getExternalId(), author.getExternalId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
