package com.example.sprint1.controller;

import com.example.sprint1.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/{userID}/follow/{userIdToFollow}")
    public ResponseEntity<?> postNewFollower(@PathVariable Integer userID, @PathVariable Integer userIdToFollow){
        return new ResponseEntity<>(userService.addFollower(userID,userIdToFollow), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.getFollowerCount(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowerList(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.getFollowerList(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<?> getFollowedList(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.getFollowedList(userId),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> setUnfollow(@PathVariable Integer userId,@PathVariable Integer userIdToUnfollow){
        return new ResponseEntity<>(userService.setUnfollow(userId,userIdToUnfollow),HttpStatus.NO_CONTENT);
    }

    @GetMapping("{userId}/followers/list-ordered")
    public ResponseEntity<?> getFollowerListOrderByName(@PathVariable Integer userId,@RequestParam String order){
        return new ResponseEntity<>(userService.getFollowersOrdered(userId,order),HttpStatus.OK);
    }
}
