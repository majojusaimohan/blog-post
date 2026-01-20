package com.ashford.blog.services;

import com.ashford.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

    List<Tag> getTags();
    List<Tag> createTags(Set<String> tags);
    void deleteTag(UUID id);
}
