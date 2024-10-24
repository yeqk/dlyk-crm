package io.github.yeqk97.dlykserver.config.handler;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.service.RedisService;
import io.github.yeqk97.dlykserver.utils.JSONUtils;
import io.github.yeqk97.dlykserver.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.github.yeqk97.dlykserver.utils.Constants.REDIS_JWT_KEY;

@Component
public class MyLogoutSuccessHandler  implements LogoutSuccessHandler {

    private RedisService redisService;

    public MyLogoutSuccessHandler(final RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        TUser user = (TUser) authentication.getPrincipal();
        redisService.deleteValue(REDIS_JWT_KEY + user.getId());
        R result = R.OK();
        String resultJson = JSONUtils.toJson(result);
        ResponseUtils.write(response, resultJson);
    }
}
