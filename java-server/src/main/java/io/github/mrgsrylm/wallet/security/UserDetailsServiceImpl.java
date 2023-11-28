package io.github.mrgsrylm.wallet.security;

import io.github.mrgsrylm.wallet.model.UserModel;
import io.github.mrgsrylm.wallet.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static io.github.mrgsrylm.wallet.common.Constants.NOT_FOUND_USERNAME;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format(NOT_FOUND_USERNAME, username)));
        return new UserDetailsImpl(userModel);
    }
}
