package com.example.sprint1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFollowersCountTest() throws Exception {
        Integer userId = 3;// some user id

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", userId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{\"user_id\": 3, \"user_name\": \"user3\", \"followers_count\": 2}"));
    }

    @Test
    public void getFollowerListTest() throws Exception {
        Integer userId = 7;// some user id

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/count", userId)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andExpect(MockMvcResultMatchers.content().json("{\"message\": \"No se encontró al vendedor\"}"));
        };
}
