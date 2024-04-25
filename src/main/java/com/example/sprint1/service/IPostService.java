package com.example.sprint1.service;

import com.example.sprint1.dto.PostDto;
import com.example.sprint1.dto.PostForListDto;
import com.example.sprint1.model.Post;
import java.util.List;

public interface IPostService {
    // Begin modify Leonardo
    // buscar post por ID
    Object addPost(PostDto postDto) throws IllegalArgumentException;
    Post save (Post post);
    List<Post> findAll();
    Post findById(int id);
    //ends modify Leonardo

    List<PostForListDto> followedList(Integer userId, String order);

    Object postPromo(PostDto postDto);

    Object quantityPromo(Integer user_id);

    Object getPromo(Integer user_id);
}
