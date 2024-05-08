package com.example.sprint1.service;


import com.example.sprint1.dto.CountFollowersUserDto;
import com.example.sprint1.exception.NotFoundException;
import com.example.sprint1.model.User;
import com.example.sprint1.repository.UserRepositoryImpl;
import com.example.sprint1.repository.UserRepositoryTest;
import com.example.sprint1.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    UserServiceImpl userService;



    @ParameterizedTest
    @DisplayName("Test getFollowers")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testGetFollowers(List<User> users) {
        // arrange
        Mockito.when(userRepository.findAll()).thenReturn(users);
        // act
        CountFollowersUserDto expected = userService.getFollowerCount(3);
        // assert
        Assertions.assertEquals(2, expected.getCount());
        Assertions.assertEquals("user3", expected.getUserName());
        Assertions.assertEquals(3, expected.getUserId());
    }

    @Test
    @DisplayName("Test getFollowers bad path")
    public void testGetFollowersBadPath() {
        // arrange
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
        // act
        Assertions.assertThrows(NotFoundException.class, () -> userService.getFollowerCount(3));
    }




}
