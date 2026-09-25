package com.ecol.authService.config.authEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class UserLogoutEvent extends AuthBaseEvent {
    private String userId;
    private String sessionId;
    private String email;
    private String logoutMethod;

    public UserLogoutEvent() { super(null, "USER_LOGOUT", null, null); }
}
