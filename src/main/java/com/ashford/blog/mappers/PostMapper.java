package com.ashford.blog.mappers;

import com.ashford.blog.domain.CreatePostRequest;
import com.ashford.blog.domain.dtos.CreatePostRequestDto;
import com.ashford.blog.domain.dtos.PostDto;
import com.ashford.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source="author")
    @Mapping(target = "category", source="category")
    @Mapping(target = "tags", source="tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
}
