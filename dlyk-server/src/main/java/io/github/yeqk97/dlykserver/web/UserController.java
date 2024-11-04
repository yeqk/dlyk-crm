package io.github.yeqk97.dlykserver.web;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.model.dto.UserDto;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/login/info")
    public R loginInfo(final Authentication authentication) {
        TUser user = (TUser) authentication.getPrincipal();
        return R.OK(user);
    }

    @GetMapping("/api/login/free")
    public R freeLogin() {
        return R.OK();
    }

    @GetMapping("/api/users")
    public R listUsers(@RequestParam(required = false) final Integer current) {
        Page<UserDto> users = userService.listUsers(current);
        return R.OK(users);
    }

    @GetMapping("/api/users/{id}")
    public R getUser(@PathVariable final Integer id) {
        Optional<UserDto> user = userService.getUser(id);
        return user.isPresent() ? R.OK(user) : R.FAIL("User not found");
    }

    @PostMapping("/api/users")
    public R addUser(final UserDto user, final Authentication authentication) {
        TUser createdBy = (TUser) authentication.getPrincipal();
        UserDto newUser = userService.addUser(user, createdBy);
        return R.OK(newUser);
    }

    @PutMapping("/api/users")
    public R editUser(final UserDto user, final Authentication authentication) {
        TUser editedBy = (TUser) authentication.getPrincipal();
        UserDto newUser = userService.editUser(user, editedBy);
        return R.OK(newUser);
    }

    @DeleteMapping("/api/users/{id}")
    public R deleteUser(@PathVariable final Integer id) {
        userService.deleteUser(id);
        return R.OK();
    }

    @DeleteMapping("/api/users")
    public R batchDeleteUsers(@RequestParam final List<Integer> ids) {
        userService.batchDeleteUsers(ids);
        return R.OK();
    }
}
