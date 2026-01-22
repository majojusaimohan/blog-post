package com.ashford.blog.services.impl;

import com.ashford.blog.domain.CreatePostRequest;
import com.ashford.blog.domain.PostStatus;
import com.ashford.blog.domain.entities.Category;
import com.ashford.blog.domain.entities.Post;
import com.ashford.blog.domain.entities.Tag;
import com.ashford.blog.domain.entities.User;
import com.ashford.blog.repositories.PostRepository;
import com.ashford.blog.services.CategoryService;
import com.ashford.blog.services.PostService;
import com.ashford.blog.services.TagService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private static final int  WORDS_PER_MINUTE = 200;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }


        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );

        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);


    }

    @Override
    public List<Post> getDraftPosts(User user) {
      return   postRepository.findAllByAuthorAndStatus(user,PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newpost =new Post();
        newpost.setTitle(createPostRequest.getTitle());
        newpost.setContent(createPostRequest.getContent());
        newpost.setStatus(createPostRequest.getStatus());
        newpost.setAuthor(user);
        newpost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newpost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newpost.setTags(new HashSet<>(tags));

        return postRepository.save(newpost);

    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isBlank()) {
            return 0;
        }
        String[] words = content.trim().split("\\s+");
        int wordCount = words.length;
        return Math.max(1, wordCount / WORDS_PER_MINUTE);
    }

}
