package io.github.yeqk97.dlykserver.config.handler;

import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.utils.JSONUtils;
import io.github.yeqk97.dlykserver.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        String json = JSONUtils.toJson(R.FAIL(exception.getMessage()));
        ResponseUtils.write(response, json);
    }
}
