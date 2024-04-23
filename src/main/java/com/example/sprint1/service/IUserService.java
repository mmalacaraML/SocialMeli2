package com.example.sprint1.service;
public interface IUserService {
    Object addFollower(Integer userID, Integer userIdToFollow);

    Object getFollowerCount(Integer userId);

    Object getFollowerList(Integer userId);

    Object getFollowedList(Integer userId);

    Object setUnfollow(Integer userId, Integer userIdToUnfollow);

    Object getFollowersOrdered(Integer userId, String order);
}
