package com.ashford.blog.services.impl;

import com.ashford.blog.domain.entities.Tag;
import com.ashford.blog.repositories.TagRepository;
import com.ashford.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    @Override
    public List<Tag> getTags() {
        return tagRepository.findALlWithPostCount();
    }
}
