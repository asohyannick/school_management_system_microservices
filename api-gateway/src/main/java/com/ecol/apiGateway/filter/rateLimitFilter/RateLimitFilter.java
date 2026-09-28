package com.ecol.apiGateway.filter.rateLimitFilter;
import com.ecol.apiGateway.config.rateLimitConfig.rateLimitProperties.RateLimitProperties;
import com.ecol.apiGateway.exception.errorResponseWriter.ErrorResponseWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class RateLimitFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redis;
    private final RedisScript<List> script;
    private final RateLimitProperties properties;
    private final ErrorResponseWriter writer;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        String username = currentUsername();

        List<String> keys = (username != null)
                ? List.of("rate:count:user:" + username, "rate:ips:user:" + username)
                : List.of("rate:count:ip:" + ip);

        long count;
        long secondsUntilReset;
        try {
            List<?> result = redis.execute(script, keys,
                    String.valueOf(properties.window().toSeconds()), ip);
            count = ((Number) result.get(0)).longValue();
            secondsUntilReset = ((Number) result.get(1)).longValue();
        } catch (Exception e) {
            log.warn("Rate limiter unavailable, allowing request: {}", e.getMessage());
            chain.doFilter(request, response);
            return;
        }

        long remaining = Math.max(0, properties.capacity() - count);
        response.setHeader("X-RateLimit-Limit", String.valueOf(properties.capacity()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(remaining));

        if (count > properties.capacity()) {
            response.setHeader(HttpHeaders.RETRY_AFTER, String.valueOf(Math.max(secondsUntilReset, 1)));
            writer.write(response, HttpStatus.TOO_MANY_REQUESTS,
                    "Rate limit exceeded. Try again in " + secondsUntilReset + " seconds", request);
            return;
        }

        chain.doFilter(request, response);
    }

    private String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return auth.getName();
    }
}