package io.github.yeqk97.dlykserver.service;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Page<UserDto> listUsers(Integer current);

    Optional<UserDto> getUser(Integer id);

    UserDto addUser(UserDto user, final TUser createdBy);

    UserDto editUser(UserDto user, final TUser editedBy);

    void deleteUser(Integer id);

    void batchDeleteUsers(List<Integer> ids);
}
