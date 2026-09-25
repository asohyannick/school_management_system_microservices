package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountStatusChangedEvent extends AuthBaseEvent {
    private String userId;
    private String previousStatus; // ACTIVE, BLOCKED
    private String newStatus;
    private String reason;
    private String actorId;        // admin who performed the action

    public AccountStatusChangedEvent() { super(null, "ACCOUNT_STATUS_CHANGED", null, null); }
}