package com.example.sprint1.repository;
import com.example.sprint1.model.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll();
}
