package com.example.sprint1.service;

import com.example.sprint1.dto.PostForListDto;
import com.example.sprint1.model.Post;
import com.example.sprint1.model.Product;
import com.example.sprint1.model.User;
import com.example.sprint1.repository.IUserRepository;
import com.example.sprint1.repository.PostRepositoryImpl;
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

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepositoryImpl postRepository;

    @InjectMocks
    PostServiceImpl postService;

}
