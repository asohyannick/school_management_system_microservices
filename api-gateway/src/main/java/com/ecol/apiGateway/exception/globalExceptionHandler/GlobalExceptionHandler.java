package com.ecol.apiGateway.exception.globalExceptionHandler;
import com.ecol.apiGateway.exception.badRequestException.BadRequestException;
import com.ecol.apiGateway.exception.errorResponseWriter.ErrorResponseWriter;
import com.ecol.apiGateway.exception.gatewayErrorResponse.GatewayErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorResponseWriter writer;

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GatewayErrorResponse> badRequest(BadRequestException ex, HttpServletRequest req) {
        return respond(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GatewayErrorResponse> status(ResponseStatusException ex, HttpServletRequest req) {
        return respond(HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GatewayErrorResponse> fallback(Exception ex, HttpServletRequest req) {
        log.error("Gateway error [{} {}]: {}", req.getMethod(), req.getRequestURI(), ex.getMessage(), ex);
        return respond(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", req);
    }

    private ResponseEntity<GatewayErrorResponse> respond(HttpStatus status, String msg, HttpServletRequest req) {
        return ResponseEntity.status(status).body(writer.build(status, msg, req));
    }
}