package com.example.sprint1.repository;

import com.example.sprint1.model.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class PostRepositoryImpl implements IPostRepository{


    private static List<Post> listOfPosts;

    public PostRepositoryImpl() throws IOException{
        loadDatabase();
    }

    private void loadDatabase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts;

        file= ResourceUtils.getFile("classpath:posts.json");
        posts = objectMapper.readValue(file,new TypeReference<List<Post>>(){});

        listOfPosts = posts;
    }
}
