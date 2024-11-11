package io.github.yeqk97.dlykserver.service.impl;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.model.dto.UserDto;
import io.github.yeqk97.dlykserver.repository.TUserRepository;
import io.github.yeqk97.dlykserver.repository.specification.UserSpecification;
import io.github.yeqk97.dlykserver.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.github.yeqk97.dlykserver.utils.Constants.PAGE_SIZE;

@Service
public class UserServiceImpl implements UserService {

    private final TUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public static Function<TUser, UserDto> toUserDto = u -> {
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
        TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findAll(UserSpecification.filterByScope(user), Pageable.ofSize(PAGE_SIZE).withPage(current)).map(toUserDto);
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

    @Override
    @Transactional
    public UserDto editUser(final UserDto user, final TUser editedBy) {
        TUser userToUpdate = userRepository.findById(user.getId()).orElseThrow(() -> new InvalidParameterException("User not found"));
        userToUpdate.setLoginAct(user.getLoginAct());
        if (StringUtils.hasText(user.getLoginPwd())) {
            userToUpdate.setLoginPwd(passwordEncoder.encode(user.getLoginPwd()));
        }
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setAccountNoExpired(user.getAccountNoExpired());
        userToUpdate.setCredentialsNoExpired(user.getCredentialsNoExpired());
        userToUpdate.setAccountNoLocked(user.getAccountNoLocked());
        userToUpdate.setAccountEnabled(user.getAccountEnabled());
        userToUpdate.setEditBy(editedBy);
        userToUpdate.setEditTime(Instant.now());
        TUser updatedUser = userRepository.save(userToUpdate);
        return toUserDto.apply(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(final Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void batchDeleteUsers(final List<Integer> ids) {
        userRepository.deleteAllByIdInBatch(ids);
    }
}
