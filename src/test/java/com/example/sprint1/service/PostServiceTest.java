package com.example.sprint1.service;

import com.example.sprint1.repository.PostRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepositoryImpl postRepository;

    @InjectMocks
    PostServiceImpl postService;

}
