package com.ecol.apiGateway.filter.gatewayAuthEntryPoint;
import com.ecol.apiGateway.exception.errorResponseWriter.ErrorResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GatewayAuthEntryPoint implements AuthenticationEntryPoint {

    private final ErrorResponseWriter writer;

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException ex) throws IOException {
        writer.write(response, HttpStatus.UNAUTHORIZED,
                "Authentication required or token is invalid", request);
    }
}