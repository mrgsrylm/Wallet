package com.poywallet.poywalletbackend.service.impl;

import com.poywallet.poywalletbackend.exception.NoSuchElementFoundException;
import com.poywallet.poywalletbackend.model.User;
import com.poywallet.poywalletbackend.repository.UserRepository;
import com.poywallet.poywalletbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used for User related operations
 */
@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Fetches a single user reference (entity) by the given id
     *
     * @param id
     * @return User
     */
    @Override
    public User getReferenceById(long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        if (result.isPresent()) {
            return result.get();
        }else{
            throw new NoSuchElementFoundException();
        }
    }
}
