package com.example.sprint1.util;

import com.example.sprint1.dto.PostDto;
import com.example.sprint1.dto.PostForListDto;
import com.example.sprint1.dto.UserDto;
import com.example.sprint1.model.Post;
import com.example.sprint1.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Utils {


    public static Stream<List<User>> userProvider() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users;

        file= ResourceUtils.getFile("classpath:users.json");
        users = objectMapper.readValue(file,new TypeReference<List<User>>(){});

        return Stream.of(users);
    }

    public static Stream<List<Post>> postProvider() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts;

        file= ResourceUtils.getFile("classpath:posts.json");
        posts = objectMapper.readValue(file,new TypeReference<List<Post>>(){});

        return Stream.of(posts);
    }

    public static Stream<List<PostDto>> postDtoProvider() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<PostDto> posts;

        file= ResourceUtils.getFile("classpath:posts.json");
        posts = objectMapper.readValue(file,new TypeReference<List<PostDto>>(){});

        return Stream.of(posts);
    }


    public static Stream<List<UserDto>> userDtoProvider() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDto> users;

        file= ResourceUtils.getFile("classpath:users.json");
        users = objectMapper.readValue(file,new TypeReference<List<UserDto>>(){});

        return Stream.of(users);
    }

}
