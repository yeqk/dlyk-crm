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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.github.yeqk97.dlykserver.result.CodeEnum.TOKEN_NOT_MATCH;
import static io.github.yeqk97.dlykserver.result.CodeEnum.TOKEN_EMPTY;
import static io.github.yeqk97.dlykserver.result.CodeEnum.TOKEN_EXPIRED;
import static io.github.yeqk97.dlykserver.result.CodeEnum.TOKEN_INVALID;
import static io.github.yeqk97.dlykserver.utils.Constants.DEFAULT_EXPIRE_TIME;
import static io.github.yeqk97.dlykserver.utils.Constants.REDIS_JWT_KEY;
import static io.github.yeqk97.dlykserver.utils.Constants.REMEMBER_ME_EXPIRE_TIME;

@Component
public class TokenVerifyFilter extends OncePerRequestFilter {

    private RedisService redisService;

    /**
     * Provided by spring boot
     */
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public TokenVerifyFilter(final RedisService redisService, final ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.redisService = redisService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(Constants.LOGIN_URI)) {
            filterChain.doFilter(request, response);
        } else {
            String token = request.getHeader("Authorization");

            if (token == null || token.isEmpty()) {
                R result = R.FAIL(TOKEN_EMPTY);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            if (!JWTUtils.verifyJWT(token)) {
                R result = R.FAIL(TOKEN_INVALID);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            TUser user = JWTUtils.parseUserFromJWT(token);
            String redisToken = (String) redisService.getValue(REDIS_JWT_KEY + user.getId());

            if (redisToken == null || redisToken.isEmpty()) {
                R result = R.FAIL(TOKEN_EXPIRED);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            if (!token.equals(redisToken)) {
                R result = R.FAIL(TOKEN_NOT_MATCH);
                String resultJson = JSONUtils.toJson(result);
                ResponseUtils.write(response, resultJson);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getLoginPwd(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Refresh token expiration date async
            threadPoolTaskExecutor.execute(() -> {
                String key = REDIS_JWT_KEY + user.getId();
                if (Boolean.parseBoolean(request.getHeader("rememberMe"))) {
                    redisService.expire(key, REMEMBER_ME_EXPIRE_TIME, TimeUnit.SECONDS);
                } else {
                    redisService.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
                }
            });

            filterChain.doFilter(request, response);
        }

    }
}
