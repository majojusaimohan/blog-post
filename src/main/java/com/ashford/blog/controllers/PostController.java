package com.ashford.blog.controllers;

import com.ashford.blog.domain.dtos.PostDto;
import com.ashford.blog.domain.entities.Post;
import com.ashford.blog.mappers.PostMapper;
import com.ashford.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private  final PostService postService;
    private final PostMapper postMapper;

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
}
