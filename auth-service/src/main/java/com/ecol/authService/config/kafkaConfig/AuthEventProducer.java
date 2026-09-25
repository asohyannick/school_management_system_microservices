package com.ecol.authService.config.kafkaConfig;

import com.ecol.authService.config.authEvent.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.user-registered}") private String userRegisteredTopic;
    @Value("${spring.kafka.topics.user-login}")      private String userLoginTopic;
    @Value("${spring.kafka.topics.user-logout}")     private String userLogoutTopic;
    @Value("${spring.kafka.topics.otp-events}")      private String otpEventsTopic;
    @Value("${spring.kafka.topics.password-events}") private String passwordEventsTopic;
    @Value("${spring.kafka.topics.account-status}")  private String accountStatusTopic;
    @Value("${spring.kafka.topics.magiclink-events}")private String magiclinkEventsTopic;
    @Value("${spring.kafka.topics.google-login}")    private String googleLoginTopic;
    @Value("${spring.kafka.topics.security-alerts}") private String securityAlertsTopic;
    @Value("${spring.kafka.topics.audit-events}")    private String auditEventsTopic;

    public void publishUserRegistered(UserRegisteredEvent event) {
        send(userRegisteredTopic, event.getUserId(), event);
    }

    public void publishUserLogin(UserLoginEvent event) {
        send(userLoginTopic, event.getUserId(), event);
        publishAudit(event.getUserId(), "LOGIN",
                event.getUserId(), event.isSuccessful() ? "SUCCESS" : "FAILURE",
                event.getIpAddress(), null);
    }

    public void publishUserLogout(UserLogoutEvent event) {
        send(userLogoutTopic, event.getUserId(), event);
        publishAudit(event.getUserId(), "LOGOUT", event.getUserId(), "SUCCESS", null, null);
    }

    public void publishOtpEvent(OtpEvent event) {
        send(otpEventsTopic, event.getUserId(), event);
    }

    public void publishPasswordEvent(PasswordEvent event) {
        send(passwordEventsTopic, event.getUserId(), event);
    }

    public void publishAccountStatus(AccountStatusChangedEvent event) {
        send(accountStatusTopic, event.getUserId(), event);
        // Also send a security alert when an account is blocked
        if ("BLOCKED".equals(event.getNewStatus())) {
            publishSecurityAlert(event.getUserId(), "ACCOUNT_BLOCKED", "HIGH",
                    Map.of("reason", event.getReason(), "actor", event.getActorId()));
        }
    }

    public void publishMagicLinkEvent(MagicLinkEvent event) {
        send(magiclinkEventsTopic, event.getUserId(), event);
    }

    public void publishGoogleLogin(GoogleLoginEvent event) {
        send(googleLoginTopic, event.getUserId(), event);
    }

    public void publishSecurityAlert(String userId, String alertType,
                                     String severity, Map<String, Object> metadata) {
        SecurityAlertEvent event = new SecurityAlertEvent();
        event.setUserId(userId);
        event.setAlertType(alertType);
        event.setSeverity(severity);
        event.setMetadata(metadata);
        send(securityAlertsTopic, userId, event);
    }

    public void publishAudit(String actorId, String action, String resource,
                             String outcome, String ipAddress, Map<String, Object> metadata) {
        AuditEvent event = new AuditEvent();
        event.setActorId(actorId);
        event.setAction(action);
        event.setResource(resource);
        event.setOutcome(outcome);
        event.setIpAddress(ipAddress);
        event.setMetadata(metadata);
        send(auditEventsTopic, actorId, event);
    }

    private void send(String topic, String key, Object payload) {
        kafkaTemplate.send(topic, key, payload)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish event to topic={} key={}: {}",
                                topic, key, ex.getMessage());
                        // Consider: dead-letter queue, retry, or outbox pattern here
                    } else {
                        log.debug("Published event to topic={} partition={} offset={}",
                                topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}