package io.github.yeqk97.dlykserver.web;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.result.R;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UesrController {

    @GetMapping("/api/login/info")
    public R loginInfo(final Authentication authentication) {
        TUser user = (TUser) authentication.getPrincipal();
        return R.OK(user);
    }
}
