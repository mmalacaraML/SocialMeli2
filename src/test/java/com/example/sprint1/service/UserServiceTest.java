package com.example.sprint1.service;


import com.example.sprint1.dto.FollowListDto;
import com.example.sprint1.exception.BadRequestException;
import com.example.sprint1.model.Post;
import com.example.sprint1.model.User;
import com.example.sprint1.repository.UserRepositoryImpl;
import com.example.sprint1.repository.PostRepositoryImpl;
import com.example.sprint1.repository.UserRepositoryTest;
import com.example.sprint1.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userService.getUsers(), users);
        // act

        // assert
    }

    /**
     * T-0001
     * Verify the successful addition of a follower when both users exist and are not already
     * following each other.
     */
    @ParameterizedTest
    @DisplayName("Test addFollower - Success when Both Users Exist and are Not Already Following Each Other")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testAddFollower_Success(List<User> users) {
        Integer userId = 1;
        Integer userToFollowId = 2;

        // Arrange
        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToFollow = users.stream().filter(u -> u.getId().equals(userToFollowId)).findFirst().orElse(null);

        // Mocking UserRepository to return the user objects
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.findUserById(userToFollowId)).thenReturn(userToFollow);

        // Behavior of updating followers
        Mockito.doAnswer(invocation -> {
            User u = invocation.getArgument(0);
            User uf = invocation.getArgument(1);
            u.getFollowed().add(uf.getId());
            uf.getFollowers().add(u.getId());
            return null;
        }).when(userRepository).updateUserFollower(Mockito.any(User.class), Mockito.any(User.class));

        // Act
        // Execute the add follower functionality
        userService.addFollower(userId, userToFollowId);

        // Assert
        // Assertions to verify the correct behavior
        assertTrue(user.getFollowed().contains(userToFollowId));
        assertTrue(userToFollow.getFollowers().contains(userId));
        Mockito.verify(userRepository).updateUserFollower(user, userToFollow);
    }

    /**
     * T-0001
     * Verify the behavior when the user attempting to follow does not exist.
     * This test ensures that a BadRequestException is thrown indicating that the user was not found.
     */
    @ParameterizedTest
    @DisplayName("Test addFollower - Fail if User Not Found")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testAddFollower_UserNotFound(List<User> users) {
        Integer userId = 10;  // User trying to follow another, does not exist
        Integer userToFollowId = 1;  // Existing user

        // Arrange
        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToFollow = users.stream().filter(u -> u.getId().equals(userToFollowId)).findFirst().orElse(null);
        // Find users
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.findUserById(userToFollowId)).thenReturn(userToFollow);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.addFollower(userId, userToFollowId);
        });

        // Assert
        assertEquals("User not found", exception.getMessage());
        Mockito.verify(userRepository, Mockito.never()).updateUserFollower(any(User.class), any(User.class));
    }

    /**
     * T-0001
     * Verify that an error is thrown when a user attempts to follow themselves.
     * This test checks for a BadRequestException with a specific message indicating that
     * following oneself is not allowed.
     */
    @ParameterizedTest
    @DisplayName("Test addFollower - Fail if User Tries to Follow Themselves")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testAddFollower_UserFollowsThemselves(List<User> users) {
        Integer userId = 1;  // User attempting to follow themselves

        // Arrange
        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.addFollower(userId, userId);
        });

        //Assert
        assertEquals("Can't follow yourself", exception.getMessage());
    }

    /**
     * T-0001
     * Verify the behavior when a user attempts to follow another user whom they already follow.
     * This test expects a BadRequestException to be thrown with a message indicating that the user is
     * already followed.
     */
    @ParameterizedTest
    @DisplayName("Test addFollower - Fail if User Already Follows the Other User")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testAddFollower_AlreadyFollowing(List<User> users) {
        Integer userId = 3;
        Integer userToFollowId = 4;

        // Arrange
        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToFollow = users.stream().filter(u -> u.getId().equals(userToFollowId)).findFirst().orElse(null);

        Mockito.when(userRepository.findUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.findUserById(userToFollowId)).thenReturn(userToFollow);

        // Act
        Exception exception = assertThrows(BadRequestException.class, () -> {
            userService.addFollower(userId, userToFollowId);
        });
        // Assert
        assertEquals("User already followed", exception.getMessage());
    }




}
