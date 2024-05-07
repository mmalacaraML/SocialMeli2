package com.example.sprint1.service;

import com.example.sprint1.dto.PostDto;
import com.example.sprint1.dto.PostForListDto;
import com.example.sprint1.model.Post;
import com.example.sprint1.model.Product;
import com.example.sprint1.model.User;
import com.example.sprint1.repository.IUserRepository;
import com.example.sprint1.repository.PostRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepositoryImpl postRepository;
    @Mock
    IUserRepository userRepository;

    @InjectMocks
    PostServiceImpl postService;
    @Test
    @DisplayName("Test order post by date asc")
    public void testFollowedListSortedByDateAsc() {
        // arrange
        Product product1 = new Product(1, "Product 1", "Type 1", "Brand 1", "Color 1", "Notes 1");
        Product product2 = new Product(2, "Product 2", "Type 2", "Brand 2", "Color 2", "Notes 2");

        List<Post> expectedPosts = List.of(
                new Post(1, 1, "19-12-2022", 1, 100.0, product1, false, 0.0),
                new Post(2, 2, "20-12-2022", 2, 200.0, product2, false, 0.0)
        );

        Mockito.when(userRepository.getUserById(1)).thenReturn(Optional.of(new User(1, "User 1", Set.of(2), Set.of(2), Set.of(1))));
        Mockito.when(postRepository.getResentPost(Mockito.anyInt())).thenReturn(expectedPosts);

        // act
        List<PostForListDto> actualPosts = postService.selectIfOrderFollowedList(1, "date_asc");
        // convert expectedPosts to PostForListDto
        ObjectMapper mapper = new ObjectMapper();
        List<PostForListDto> expectedPostsForListDto = expectedPosts.stream()
                .map(post -> mapper.convertValue(post, PostForListDto.class))
                .collect(Collectors.toList());
        // assert
        Assertions.assertEquals(expectedPosts.size(), actualPosts.size());
        for (int i = 0; i < expectedPostsForListDto.size(); i++) {
            Assertions.assertEquals(expectedPostsForListDto.get(i).getDate(), actualPosts.get(i).getDate());
        }
    }
    
    @Test
    @DisplayName("Test order post by date desc")
    public void testFollowedListSortedByDateDesc() {
        // arrange
        Product product1 = new Product(1, "Product 1", "Type 1", "Brand 1", "Color 1", "Notes 1");
        Product product2 = new Product(2, "Product 2", "Type 2", "Brand 2", "Color 2", "Notes 2");

        List<Post> expectedPosts = List.of(
                new Post(1, 1, "20-12-2022", 1, 100.0, product1, false, 0.0),
                new Post(2, 2, "19-12-2022", 2, 200.0, product2, false, 0.0)
        );

        Mockito.when(userRepository.getUserById(1)).thenReturn(Optional.of(new User(1, "User 1", Set.of(2), Set.of(2), Set.of(1))));
        Mockito.when(postRepository.getResentPost(Mockito.anyInt())).thenReturn(expectedPosts);

        // act
        List<PostForListDto> actualPosts = postService.selectIfOrderFollowedList(1, "date_desc");
        // convert expectedPosts to PostForListDto
        ObjectMapper mapper = new ObjectMapper();
        List<PostForListDto> expectedPostsForListDto = expectedPosts.stream()
                .map(post -> mapper.convertValue(post, PostForListDto.class))
                .collect(Collectors.toList());
        // assert
        Assertions.assertEquals(expectedPosts.size(), actualPosts.size());
        for (int i = 0; i < expectedPostsForListDto.size(); i++) {
            Assertions.assertEquals(expectedPostsForListDto.get(i).getDate(), actualPosts.get(i).getDate());
        }
    }
}
