package com.ecol.authService.config.authEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisteredEvent extends AuthBaseEvent {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String registrationSource;

    public UserRegisteredEvent() { super(null, "USER_REGISTERED", null, null); }
}