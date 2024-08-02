package com.sparta.teamssc.domain.chat.config;

import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.auth.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String token = accessor.getFirstNativeHeader("Authorization");

        if (StompCommand.CONNECT == accessor.getCommand()) {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    String username = JwtUtil.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (JwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        log.info("WebSocket 연결 성공: 사용자명 - {}", username);

                        accessor.setUser(authentication);
                    } else {
                        log.warn("WebSocket 연결 실패: 유효하지 않은 토큰");
                    }
                } catch (Exception e) {
                    log.error("WebSocket 연결 실패: 토큰 처리 중 에러", e);
                }
            } else {
                log.warn("WebSocket 연결 실패: 토큰이 제공되지 않음");
            }
        }
        return message;
    }
}
