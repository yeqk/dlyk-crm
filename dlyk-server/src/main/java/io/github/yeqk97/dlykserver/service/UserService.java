package io.github.yeqk97.dlykserver.service;

import io.github.yeqk97.dlykserver.model.TUser;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Page<TUser> listUsers(Integer current);

    Optional<TUser> getUser(Integer id);
}
