package io.github.yeqk97.dlykserver.service.impl;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.repository.TUserRepository;
import io.github.yeqk97.dlykserver.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final TUserRepository userRepository;

    public UserServiceImpl(TUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TUser user = userRepository.findByLoginAct(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username %s not found".formatted(username));
        }

        return user;
    }
}
