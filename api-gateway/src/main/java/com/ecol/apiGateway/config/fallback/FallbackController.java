package com.ecol.apiGateway.config.fallback;

import com.ecol.apiGateway.exception.errorResponseWriter.ErrorResponseWriter;
import com.ecol.apiGateway.exception.gatewayErrorResponse.GatewayErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fallback")
public class FallbackController {

    private final ErrorResponseWriter writer;

    @RequestMapping("/{service}")
    public ResponseEntity<GatewayErrorResponse> fallback(
            @PathVariable String service,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .header(HttpHeaders.RETRY_AFTER, "30")
                .body(writer.build(HttpStatus.SERVICE_UNAVAILABLE,
                        service + " is temporarily unavailable. Please try again shortly.",
                        request));
    }
}