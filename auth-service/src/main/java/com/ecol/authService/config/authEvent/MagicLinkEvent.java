package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class MagicLinkEvent extends AuthBaseEvent {
    private String userId;
    private String tokenId;
    private String action;
    private Instant expiresAt;

    public MagicLinkEvent() {
        super(null, "MAGIC_LINK_EVENT", null, null); }
}
