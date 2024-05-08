package com.example.sprint1.service;


import com.example.sprint1.dto.FollowListDto;
import com.example.sprint1.exception.BadRequestException;
import com.example.sprint1.exception.NotFoundException;
import com.example.sprint1.model.User;
import com.example.sprint1.repository.UserRepositoryImpl;
import com.example.sprint1.util.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    UserServiceImpl userService;


    /**
     * Unit tests T-0003
     * Tests the ascending order and descending order of the method
     * getFollowerListToString, with order name_asc and name_desc
     */
    @Test
    @DisplayName("Verify that the alphabetical sort type exists")
    public void testGetFollowerListToString(){
        //Arrange
        List<User> mockFollowerList = Arrays.asList(
                new User(2, "Manuel", new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new User(3, "Mau", new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new User(4, "Joaquin",new HashSet<>(), new HashSet<>(), new HashSet<>())
        );

        when(userRepository.getUserById(1)).thenReturn(Optional.of(new User(1, "John",new HashSet<>(), new HashSet<>(), new HashSet<>())));
        when(userRepository.getFollowersById(1)).thenReturn(mockFollowerList);

        // Act
        FollowListDto resultAsc = userService.getFollowerList(1, "name_asc");
        FollowListDto resultDesc = userService.getFollowerList(1, "name_desc");

        // Assert
        //Ascending order
        assertEquals("Joaquin", resultAsc.getFollowed().get(0).getUser_name());
        assertEquals("Manuel", resultAsc.getFollowed().get(1).getUser_name());
        assertEquals("Mau", resultAsc.getFollowed().get(2).getUser_name());

        //Descending order
        assertEquals("Mau", resultDesc.getFollowed().get(0).getUser_name());
        assertEquals("Manuel", resultDesc.getFollowed().get(1).getUser_name());
        assertEquals("Joaquin", resultDesc.getFollowed().get(2).getUser_name());
    }

    /**
     * Unit test T-0003
     * Test that any other order throws a bad exception
     */
    @Test
    @DisplayName("Verify that the alphabetical sort type doesn't exist")
    public void testGetFollowerListToStringException() {
        List<User> mockFollowerList = Arrays.asList(
                new User(2, "Manuel", new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new User(3, "Mau", new HashSet<>(), new HashSet<>(), new HashSet<>()),
                new User(4, "Joaquin",new HashSet<>(), new HashSet<>(), new HashSet<>())
        );

        when(userRepository.getUserById(1)).thenReturn(Optional.of(new User(1, "John",new HashSet<>(), new HashSet<>(), new HashSet<>())));
        when(userRepository.getFollowersById(1)).thenReturn(mockFollowerList);

        // Exception handling for invalid sort order
        assertThrows(BadRequestException.class, () -> userService.getFollowerList(1, "other_sort"));
    }

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
