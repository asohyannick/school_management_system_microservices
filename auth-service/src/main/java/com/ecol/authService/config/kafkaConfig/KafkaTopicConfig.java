package com.ecol.authService.config.kafkaConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topics.user-registered}") private String userRegistered;
    @Value("${spring.kafka.topics.user-login}")      private String userLogin;
    @Value("${spring.kafka.topics.user-logout}")     private String userLogout;
    @Value("${spring.kafka.topics.otp-events}")      private String otpEvents;
    @Value("${spring.kafka.topics.password-events}") private String passwordEvents;
    @Value("${spring.kafka.topics.account-status}")  private String accountStatus;
    @Value("${spring.kafka.topics.magiclink-events}")private String magiclinkEvents;
    @Value("${spring.kafka.topics.google-login}")    private String googleLogin;
    @Value("${spring.kafka.topics.security-alerts}") private String securityAlerts;
    @Value("${spring.kafka.topics.audit-events}")    private String auditEvents;

    private NewTopic topic(String name) {
        return TopicBuilder.name(name)
                .partitions(3)
                .replicas(2)
                .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(7 * 24 * 60 * 60 * 1000L)) // 7 days
                .build();
    }

    @Bean public NewTopic userRegisteredTopic()  { return topic(userRegistered); }
    @Bean public NewTopic userLoginTopic()        { return topic(userLogin); }
    @Bean public NewTopic userLogoutTopic()       { return topic(userLogout); }
    @Bean public NewTopic otpEventsTopic()        { return topic(otpEvents); }
    @Bean
    public NewTopic passwordEventsTopic()   { return topic(passwordEvents); }
    @Bean public NewTopic accountStatusTopic()    { return topic(accountStatus); }
    @Bean public NewTopic magiclinkEventsTopic()  { return topic(magiclinkEvents); }
    @Bean public NewTopic googleLoginTopic()      { return topic(googleLogin); }
    @Bean public NewTopic securityAlertsTopic()   { return topic(securityAlerts); }
    @Bean public NewTopic auditEventsTopic()      { return topic(auditEvents); }
}