package com.example.sprint1.service;


import com.example.sprint1.exception.BadRequestException;
import com.example.sprint1.exception.NotFoundException;
import com.example.sprint1.model.User;
import com.example.sprint1.repository.UserRepositoryImpl;
import com.example.sprint1.repository.UserRepositoryTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        // act

        // assert
    }

    // Test unfollowUser for existing user and existing user to unfollow
    @ParameterizedTest
    @DisplayName("Test unfollowUser for existing user and existing user to unfollow")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testUnfollowUForExistingUserAndExistingUserToFollow(List<User> users) {
        // arrange
        Integer userId = 3;
        Integer userIdToUnfollow = 4;

        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToUnfollow = users.stream().filter(u -> u.getId().equals(userIdToUnfollow)).findFirst().orElse(null);

        // act
        when(userRepository.getUserById(userId)).thenReturn(Optional.ofNullable(user));
        when(userRepository.getUserById(userIdToUnfollow)).thenReturn(Optional.ofNullable(userToUnfollow));
        if (!user.getFollowers().contains(userToUnfollow)) {
            user.addFollower(userToUnfollow.getId());
        }

        // assert
        userService.setUnfollow(userId, userIdToUnfollow);
        verify(userRepository, times(1)).updateUserFollowerDelete(user, userToUnfollow);
    }

    // Test unfollowUser for existing user and non-existing user to unfollow
    @ParameterizedTest
    @DisplayName("Test unfollowUser for existing user and non-existing user to unfollow")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testUnfollowUserForExistingUserAndNonExistingUserToFollow(List<User> users) {
        // arrange
        Integer userId = 3;
        Integer userIdToUnfollow = 0;

        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToUnfollow = users.stream().filter(u -> u.getId().equals(userIdToUnfollow)).findFirst().orElse(null);

        // act
        when(userRepository.getUserById(userId)).thenReturn(Optional.ofNullable(user));
        when(userRepository.getUserById(userIdToUnfollow)).thenReturn(Optional.ofNullable(userToUnfollow));

        // assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.setUnfollow(userId, userIdToUnfollow));
        assertEquals("User to unfollow not found: " + userIdToUnfollow, exception.getMessage());
        verify(userRepository, times(0)).updateUserFollowerDelete(user, userToUnfollow);
    }

    // Test unfollowUser for non-existing user and existing user to unfollow
    @ParameterizedTest
    @DisplayName("Test unfollowUser for non-existing user and existing user to unfollow")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testUnfollowUserForNonExistingUserAndExistingUserToFollow(List<User> users) {
        // arrange
        Integer userId = 0;
        Integer userIdToUnfollow = 4;

        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToUnfollow = users.stream().filter(u -> u.getId().equals(userIdToUnfollow)).findFirst().orElse(null);

        // act
        when(userRepository.getUserById(userId)).thenReturn(Optional.ofNullable(user));

        // assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.setUnfollow(userId, userIdToUnfollow));
        assertEquals("User not found: " + userId, exception.getMessage());
        verify(userRepository, times(0)).updateUserFollowerDelete(user, userToUnfollow);
    }

    // Test unfollowUser for existing user and existing user to unfollow but not followed
    @ParameterizedTest
    @DisplayName("Test unfollowUser for existing user and existing user to unfollow but not followed")
    @MethodSource("com.example.sprint1.util.Utils#userProvider")
    public void testUnfollowUserForExistingUserAndExistingUserToFollowButNotFollowed(List<User> users) {
        // arrange
        Integer userId = 3;
        Integer userIdToUnfollow = 1;

        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        User userToUnfollow = users.stream().filter(u -> u.getId().equals(userIdToUnfollow)).findFirst().orElse(null);

        // act
        when(userRepository.getUserById(userId)).thenReturn(Optional.ofNullable(user));
        when(userRepository.getUserById(userIdToUnfollow)).thenReturn(Optional.ofNullable(userToUnfollow));
        if (user.getFollowers().contains(userToUnfollow)) {
            user.deleteFollower(userToUnfollow.getId());
        }

        // assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.setUnfollow(userId, userIdToUnfollow));
        assertEquals("You are not following this user: " + userIdToUnfollow, exception.getMessage());
        verify(userRepository, times(0)).updateUserFollowerDelete(user, userToUnfollow);
    }

}
