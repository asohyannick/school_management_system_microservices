package com.ecol.authService.controller;
import com.ecol.authService.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/${api.version}/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Service", description = "Registration, OTP verification and authentication endpoints")
@CrossOrigin("*")
public class AuthController {
		private final AuthService authService;

		@GetMapping
		@ResponseStatus(HttpStatus.OK)
		@PreAuthorize(value = "USER")
		@Operation(method = "GET", tags = "Fetch all users", summary = "Fetch users", description = "Fetch users")
		public ResponseEntity<List<String>> fetchAllBooks() {
            ResponseEntity.status(HttpStatus.OK).build();
            return  ResponseEntity.ok(List.of("Book1", "Book2", "Book3"));
		}

		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		@PreAuthorize(value = "ADMIN")
		public  ResponseEntity<String> createBook(String data) {
			return ResponseEntity.ok(data);
		}
		
}