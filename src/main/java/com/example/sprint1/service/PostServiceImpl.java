package com.example.sprint1.service;

import com.example.sprint1.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements IPostService{

    @Autowired
    IProductRepository productRepository;
}
