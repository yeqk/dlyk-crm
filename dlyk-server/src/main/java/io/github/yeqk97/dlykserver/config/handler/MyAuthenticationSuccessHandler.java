package io.github.yeqk97.dlykserver.config.handler;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.service.RedisService;
import io.github.yeqk97.dlykserver.utils.JSONUtils;
import io.github.yeqk97.dlykserver.utils.JWTUtils;
import io.github.yeqk97.dlykserver.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.github.yeqk97.dlykserver.utils.Constants.DEFAULT_EXPIRE_TIME;
import static io.github.yeqk97.dlykserver.utils.Constants.REDIS_JWT_KEY;
import static io.github.yeqk97.dlykserver.utils.Constants.REMEMBER_ME_EXPIRE_TIME;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisService redisService;

    public MyAuthenticationSuccessHandler(final RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        TUser user = (TUser) authentication.getPrincipal();

        String userJson = JSONUtils.toJson(user);
        String jwt = JWTUtils.createJWT(userJson);

        String key = REDIS_JWT_KEY + user.getId();
        redisService.setValue(key, jwt);

        String rememberMe = request.getParameter("rememberMe");
        if (Boolean.parseBoolean(rememberMe)) {
            redisService.expire(key, REMEMBER_ME_EXPIRE_TIME, TimeUnit.SECONDS);
        } else {
            redisService.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        String result = JSONUtils.toJson(R.OK(jwt));
        ResponseUtils.write(response, result);
    }
}
