package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecurityAlertEvent extends AuthBaseEvent {
    private String userId;
    private String alertType;
    private String severity;
    private String ipAddress;
    private Map<String, Object> metadata;

    public SecurityAlertEvent() { super(null, "SECURITY_ALERT", null, null); }
}