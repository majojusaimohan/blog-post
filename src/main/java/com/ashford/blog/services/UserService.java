package com.ashford.blog.services;

import com.ashford.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
}
