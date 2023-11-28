package io.github.mrgsrylm.wallet.service.impl;

import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.repository.UserRepository;
import io.github.mrgsrylm.wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserModel getReferenceById(long id) {
        return repository.getReferenceById(id);
    }
}
