package com.ashford.blog.controllers;

import com.ashford.blog.domain.dtos.TagResponse;
import com.ashford.blog.domain.entities.Tag;
import com.ashford.blog.mappers.TagMapper;
import com.ashford.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        // Implementation for retrieving all tags goes here
        List<Tag> tags = tagService.getTags();
       List<TagResponse> tagResponses= tags.stream().map(tagMapper::toTagResponse).toList();

        return ResponseEntity.ok(tagResponses);
    }
}
