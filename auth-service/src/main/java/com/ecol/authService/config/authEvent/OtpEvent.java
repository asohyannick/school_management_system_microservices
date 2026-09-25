package com.ecol.authService.config.authEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OtpEvent extends AuthBaseEvent {
    private String userId;
    private String otpType;
    private String status;

    public OtpEvent() { super(null, "OTP_EVENT", null, null); }
}