package io.github.yeqk97.dlykserver.service.impl;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.repository.TUserRepository;
import io.github.yeqk97.dlykserver.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.github.yeqk97.dlykserver.utils.Constants.PAGE_SIZE;

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

    @Override
    public Page<TUser> listUsers(final Integer current) {
        return userRepository.findAll(Pageable.ofSize(PAGE_SIZE).withPage(current));
    }

    @Override
    public Optional<TUser> getUser(final Integer id) {
        return userRepository.findUserDetailById(id);
    }

}
