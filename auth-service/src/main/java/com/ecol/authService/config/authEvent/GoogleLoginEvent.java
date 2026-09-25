package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoogleLoginEvent extends AuthBaseEvent {
    private String userId;
    private String email;
    private String firebaseUid;
    private String displayName;
    private String photoUrl;
    private boolean firstLogin;
    private String ipAddress;
    private String userAgent;

    public GoogleLoginEvent() { super(null, "GOOGLE_LOGIN", null, null); }
}