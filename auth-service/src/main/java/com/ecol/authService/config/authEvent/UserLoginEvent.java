package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserLoginEvent extends AuthBaseEvent {
    private String userId;
    private String email;
    private String loginMethod;
    private String ipAddress;
    private String userAgent;
    private boolean successful;
    private String failureReason;

    public UserLoginEvent() { super(null, "USER_LOGIN", null, null); }
}