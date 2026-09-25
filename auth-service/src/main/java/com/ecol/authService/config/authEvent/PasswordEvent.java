package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordEvent extends AuthBaseEvent {
    private String userId;
    private String email;
    private String eventType;
    private String ipAddress;
    private String resetMethod;

    public PasswordEvent() { super(null, "PASSWORD_EVENT", null, null); }
}
