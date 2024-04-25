package com.example.sprint1.service;

import java.util.List;

import com.example.sprint1.dto.FollowListDto;
import com.example.sprint1.dto.FollowerListDto;
import com.example.sprint1.dto.FollowerUsersDto;
import com.example.sprint1.model.User;

public interface IUserService {
    void addFollower(Integer userID, Integer userIdToFollow);

    Object getFollowerCount(Integer userId);

    FollowerListDto getFollowerList(Integer userId, String order);

    FollowListDto getFollowedList(Integer userId);

    Object setUnfollow(Integer userId, Integer userIdToUnfollow);

    Object getFollowersOrdered(Integer userId, String order);

    List<User> getUsers();

    FollowerUsersDto convertToFollowUserDto(User user);

    Object getFollowedOrdered(Integer userId, String order);

}
