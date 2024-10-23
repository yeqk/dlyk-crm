package io.github.yeqk97.dlykserver.config.filter;

import io.github.yeqk97.dlykserver.model.TUser;
import io.github.yeqk97.dlykserver.result.R;
import io.github.yeqk97.dlykserver.service.RedisService;
import io.github.yeqk97.dlykserver.utils.Constants;
import io.github.yeqk97.dlykserver.utils.JSONUtils;
import io.github.yeqk97.dlykserver.utils.JWTUtils;
import io.github.yeqk97.dlykserver.utils.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static io.github.yeqk97.dlykserver.result.CodeEnum.LOGIN_JWT_NO_MATCH;
import static io.github.yeqk97.dlykserver.result.CodeEnum.TOKEN_IS_EMPTY;
import static io.github.yeqk97.dlykserver.result.CodeEnum.TOKEN_IS_EXPIRE;
import static io.github.yeqk97.dlykserver.utils.Constants.REDIS_JWT_KEY;

@Component
public class TokenVerifyFilter extends OncePerRequestFilter {

    private RedisService redisService;

    public TokenVerifyFilter(final RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(Constants.LOGIN_URI)) {
            filterChain.doFilter(request, response);
        } else {
            String token = request.getHeader("Authorization");

            if (token == null || token.isEmpty()) {
                R result = R.FAIL(TOKEN_IS_EMPTY);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            TUser user = JWTUtils.parseUserFromJWT(token);
            String redisToken = (String) redisService.getValue(REDIS_JWT_KEY + user.getId());

            if (redisToken == null || redisToken.isEmpty()) {
                R result = R.FAIL(TOKEN_IS_EXPIRE);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            if (!token.equals(redisToken)) {
                R result = R.FAIL(LOGIN_JWT_NO_MATCH);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getLoginPwd());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }

    }
}
