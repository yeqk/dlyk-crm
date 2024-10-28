package io.github.yeqk97.dlykserver.web;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.result.CodeEnum;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public R listUsers(@RequestParam(required = false) Integer current) {
        Page<TUser> users = userService.listUsers(current);
        return R.OK(users);
    }

    @GetMapping("/api/users/{id}")
    public R getUser(@PathVariable Integer id) {
        Optional<TUser> user = userService.getUser(id);
        return user.isPresent() ? R.OK(user.get()) : R.FAIL("User not found");
    }
}
