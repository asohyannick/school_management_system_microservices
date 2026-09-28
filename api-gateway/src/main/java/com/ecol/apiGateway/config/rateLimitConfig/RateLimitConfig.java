package com.ecol.apiGateway.config.rateLimitConfig;
import com.ecol.apiGateway.config.rateLimitConfig.rateLimitProperties.RateLimitProperties;
import com.ecol.apiGateway.exception.errorResponseWriter.ErrorResponseWriter;
import com.ecol.apiGateway.filter.rateLimitFilter.RateLimitFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitConfig {

    /*
     * KEYS[1] = request counter for this user/IP
     * KEYS[2] = (optional) set of IPs used by this user
     * ARGV[1] = window in seconds, ARGV[2] = client IP
     * Returns {currentCount, secondsUntilWindowResets}
     */
    private static final String SCRIPT = """
            local current = redis.call('INCR', KEYS[1])
            if current == 1 then
                redis.call('EXPIRE', KEYS[1], ARGV[1])
            end
            if #KEYS > 1 then
                redis.call('SADD', KEYS[2], ARGV[2])
                redis.call('EXPIRE', KEYS[2], ARGV[1])
            end
            return { current, redis.call('TTL', KEYS[1]) }
            """;

    @Bean
    @SuppressWarnings("rawtypes")
    public RedisScript<List> rateLimitScript() {
        return RedisScript.of(SCRIPT, List.class);
    }

    @Bean
    @SuppressWarnings("rawtypes")
    public RateLimitFilter rateLimitFilter(StringRedisTemplate redis,
                                           RedisScript<List> rateLimitScript,
                                           RateLimitProperties properties,
                                           ErrorResponseWriter writer) {
        return new RateLimitFilter(redis, rateLimitScript, properties, writer);
    }

    /** Stop Spring Boot auto-registering the filter; it runs inside the security chain instead. */
    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration(RateLimitFilter filter) {
        FilterRegistrationBean<RateLimitFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}