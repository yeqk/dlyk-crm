package io.github.yeqk97.dlykserver.service.impl;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.model.dto.UserDto;
import io.github.yeqk97.dlykserver.repository.TUserRepository;
import io.github.yeqk97.dlykserver.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

import static io.github.yeqk97.dlykserver.utils.Constants.PAGE_SIZE;

@Service
public class UserServiceImpl implements UserService {

    private final TUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private static Function<TUser, UserDto> toUserDto = u -> {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(u, userDto);
        userDto.setCreateBy(u.getCreateBy() == null ? null : u.getCreateBy().getName());
        userDto.setEditBy(u.getEditBy() == null ? null : u.getEditBy().getName());
        return userDto;
    };


    public UserServiceImpl(TUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Page<UserDto> listUsers(final Integer current) {
        return userRepository.findAll(Pageable.ofSize(PAGE_SIZE).withPage(current)).map(toUserDto);
    }

    @Override
    public Optional<UserDto> getUser(final Integer id) {
        return userRepository.findUserDetailById(id).map(toUserDto);
    }

    @Override
    @Transactional
    public UserDto addUser(final UserDto user, final TUser createdBy) {
        TUser newUser = new TUser();
        BeanUtils.copyProperties(user, newUser);
        newUser.setLoginPwd(passwordEncoder.encode(user.getLoginPwd()));
        newUser.setCreateBy(createdBy);
        newUser.setCreateTime(Instant.now());
        return toUserDto.apply(userRepository.save(newUser));
    }
}
