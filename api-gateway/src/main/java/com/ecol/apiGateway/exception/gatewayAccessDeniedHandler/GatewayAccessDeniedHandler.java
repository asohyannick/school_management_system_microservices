package com.ecol.apiGateway.exception.gatewayAccessDeniedHandler;

import com.ecol.apiGateway.exception.errorResponseWriter.ErrorResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GatewayAccessDeniedHandler implements AccessDeniedHandler {

    private final ErrorResponseWriter writer;

    @Override
    public void handle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                       @NonNull AccessDeniedException ex) throws IOException {
        writer.write(response, HttpStatus.FORBIDDEN,
                "You do not have permission to access this resource", request);
    }
}