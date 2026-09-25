package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditEvent extends AuthBaseEvent {
    private String actorId;
    private String action;
    private String resource;     // user ID or entity affected
    private String outcome;
    private String ipAddress;
    private Map<String, Object> metadata;

    public AuditEvent() { super(null, "AUDIT_EVENT", null, null); }
}