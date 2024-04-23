package com.example.sprint1.service;

import com.example.sprint1.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    IUserRepository userRepository;

    @Override
    public Object addFollower(Integer userID, Integer userIdToFollow) {
        return null;
    }

    @Override
    public Object getFollowerCount(Integer userId) {
        return null;
    }

    @Override
    public Object getFollowerList(Integer userId) {
        return null;
    }

    @Override
    public Object getFollowedList(Integer userId) {
        return null;
    }

    @Override
    public Object setUnfollow(Integer userId, Integer userIdToUnfollow) {
        return null;
    }

    @Override
    public Object getFollowersOrdered(Integer userId, String order) {
        return null;
    }
}
