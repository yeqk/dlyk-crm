package io.github.yeqk97.dlykserver.config.handler;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.utils.JsonUtils;
import io.github.yeqk97.dlykserver.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final StringRedisTemplate redisTemplate;

    public MyAuthenticationSuccessHandler(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        TUser user = (TUser) authentication.getPrincipal();

        String json = JsonUtils.toJson(R.OK(user));

        ResponseUtils.write(response, json);
    }
}
