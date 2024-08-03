package com.sparta.teamssc.domain.chat.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketSecurityContextChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        SecurityContext securityContext = (SecurityContext) accessor.getSessionAttributes()
                .get("SPRING_SECURITY_CONTEXT");

        if (securityContext != null) {
            SecurityContextHolder.setContext(securityContext);
            log.info("preSend: SecurityContext: {}", securityContext.getAuthentication());
        } else {
            log.warn("preSend: SecurityContext 찾을수 없다.");
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("postSend: SecurityContext 지우기: {}", SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext();

    }
}
