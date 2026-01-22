package com.ashford.blog.controllers;

import com.ashford.blog.domain.CreatePostRequest;
import com.ashford.blog.domain.dtos.CreatePostRequestDto;
import com.ashford.blog.domain.dtos.PostDto;
import com.ashford.blog.domain.entities.Post;
import com.ashford.blog.domain.entities.User;
import com.ashford.blog.mappers.PostMapper;
import com.ashford.blog.services.PostService;
import com.ashford.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private  final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId
            ) {
        // Implementation for retrieving all posts goes here
      List<Post> posts=  postService.getAllPosts(categoryId,tagId);
        List<PostDto> postDto = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDto);
    }

    @GetMapping(path="/drafts")
    public ResponseEntity<List<PostDto>> getDraft(@RequestAttribute UUID userId){
        User loggedInUser = userService.getUserById(userId);
        List<Post> draftPosts=postService.getDraftPosts(loggedInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);

    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId){
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto dto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }


}
