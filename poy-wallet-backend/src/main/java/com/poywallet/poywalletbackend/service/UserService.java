package com.poywallet.poywalletbackend.service;

import com.poywallet.poywalletbackend.model.User;

import java.util.Optional;

public interface UserService {
    User getReferenceById(long id);
    User findByUsername(String username);
}
