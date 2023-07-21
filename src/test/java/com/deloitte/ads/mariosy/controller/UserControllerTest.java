package com.deloitte.ads.mariosy.controller;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.entity.UserDTO;
import com.deloitte.ads.mariosy.service.MarioService;
import com.deloitte.ads.mariosy.service.UserService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    private static String url = "/api/v1/users";

    @Test
    void getUsersTest() throws Exception {
        // given
        User user1 = new User("Mateusz", "mateusz@gmail.com");
        User user2 = new User("Bartosz", "bartek@gmail.com");
        User user3 = new User("Kamil", "kamil@gmail.com");

        Set<User> users = Set.of(user1, user2, user3);

        // when then
        when(userService.getUsers()).thenReturn(users);
        mvc.perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].externalId").exists())
                .andExpect(jsonPath("$[0].username").exists())
                .andExpect(jsonPath("$[0].email").exists());
    }

    @Test
    void getUserByIdTest() throws Exception {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");

        // when then
        when(userService.getUserByExternalId(user.getExternalId())).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders
                        .get(url + "/{externalId}", user.getExternalId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.externalId").value(user.getExternalId().toString()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void getUserReceivedMariosTest() throws Exception {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        User author = new User("Mateusz", "mateusz@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author);
        user.addMarios(marios);


        // when then
        when(userService.getUsersReceivedMariosy(user.getExternalId())).thenReturn(user.getReceivedMarios());
        mvc.perform(MockMvcRequestBuilders
                        .get(url + "/{externalId}/marios/received", user.getExternalId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].externalId").value(marios.getExternalId().toString()))
                .andExpect(jsonPath("$[0].author").value(author.getExternalId().toString()))
                .andExpect(jsonPath("$[0].characterName").value(marios.getCharacterName()));
    }

    @Test
    void getUserGivenMariosTest() throws Exception {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        User author = new User("Mateusz", "mateusz@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author);
        user.addMarios(marios);


        // when then
        when(userService.getUsersGivenMariosy(author.getExternalId())).thenReturn(author.getGivenMarios());
        mvc.perform(MockMvcRequestBuilders
                        .get(url + "/{externalId}/marios/given", author.getExternalId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].externalId").value(marios.getExternalId().toString()))
                .andExpect(jsonPath("$[0].author").value(author.getExternalId().toString()))
                .andExpect(jsonPath("$[0].characterName").value(marios.getCharacterName()));
    }


    @Test
    void addUserTest() throws Exception {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        UserDTO userDTO = new UserDTO("Mateusz", "mateusz@gmail.com");

        // when then
        when(userService.addUser(any(UserDTO.class))).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders
                        .post(url + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.externalId").value(user.getExternalId().toString()))
                .andExpect(jsonPath("$.username").value("Mateusz"))
                .andExpect(jsonPath("$.email").value("mateusz@gmail.com"));
    }

    @Test
    void deleteUserTest() throws Exception {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");

        // when then
        mvc.perform(MockMvcRequestBuilders
                        .delete(url + "/delete/{userExternalId}", user.getExternalId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(user.getExternalId());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
