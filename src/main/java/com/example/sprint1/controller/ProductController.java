package com.example.sprint1.controller;

import com.example.sprint1.dto.PostDto;
import com.example.sprint1.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @PostMapping("/products/post")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(productService.addPost(postDto) , HttpStatus.CREATED);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> followedList(@PathVariable Integer userId){
        return new ResponseEntity<>(productService.followedList(userId), HttpStatus.OK);
    }


    @GetMapping("/followed/{userId}/list-ordered")
    public ResponseEntity<?> orderedList(@PathVariable Integer userId, @RequestParam String order){
        return new ResponseEntity<>(productService.orderedList(userId, order), HttpStatus.OK);
    }


    @PostMapping("/promo-post")
    public ResponseEntity<?> postPromo(@RequestBody PostDto postDto){
        return new ResponseEntity<>(productService.postPromo(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/promo-post/count")
    public ResponseEntity<?> quantityPromo(@RequestParam Integer user_id){
        return new ResponseEntity<>(productService.quantityPromo(user_id), HttpStatus.OK);
    }

    @GetMapping("/promo-post/list")
    public ResponseEntity<?> getPromo(@RequestParam Integer user_id){
        return new ResponseEntity<>(productService.getPromo(user_id), HttpStatus.OK);
    }
}
