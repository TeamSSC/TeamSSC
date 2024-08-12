package com.sparta.teamssc.domain.chat.config;

import com.sparta.teamssc.domain.user.auth.service.UserDetailsServiceImpl;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
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

        // StompHeaderAccessor로 stomp 헤더에 접근하기
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // 헤더에서 토큰 가져오기
        String token = accessor.getFirstNativeHeader("Authorization");

        // STOMP 연결 요청
        if (StompCommand.CONNECT == accessor.getCommand()) {
            // 연결 전에 기존 SecurityContext를 초기화
            SecurityContextHolder.clearContext();

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    //1. 토큰에서 사용자 이름 가져오기
                    String username = JwtUtil.getUsernameFromToken(token);
                    //2. 사용자 이름으로 UserDetails 가져오기
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 토큰 유효성 검사
                    if (JwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        // 빈 SecurityContext에 인증정보 설정
                        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                        securityContext.setAuthentication(authentication);
                        SecurityContextHolder.setContext(securityContext);

                        accessor.setUser(authentication);
                        accessor.getSessionAttributes().put("SPRING_SECURITY_CONTEXT", securityContext);

                        log.info("1 WebSocket 연결 성공: 사용자명 - {}", username);
                    } else {
                        log.warn("WebSocket 연결 실패: 유효하지 않은 토큰");
                    }
                } catch (Exception e) {
                    log.error("WebSocket 연결 실패: 토큰 처리 중 에러", e);
                }
            } else {
                log.warn("WebSocket 연결 실패: 토큰이 제공되지 않음");
            }
        } else if (StompCommand.SEND == accessor.getCommand() || StompCommand.SUBSCRIBE == accessor.getCommand()) {
            // SecurityContextHolder에 이미 설정된 인증 정보를 사용
            SecurityContext securityContext = (SecurityContext) accessor.getSessionAttributes().get("SPRING_SECURITY_CONTEXT");
            if (securityContext != null) {
                SecurityContextHolder.setContext(securityContext);
                log.info("SEND/SUBSCRIBE 명령 처리 중 인증 정보 유지: {}", securityContext.getAuthentication());
            } else {
                log.warn("SEND/SUBSCRIBE 명령 처리 중 SecurityContext를 찾을 수 없음");
            }
        }
        log.info("2 preSend: 현재 SecurityContextHolder: {}", SecurityContextHolder.getContext().getAuthentication());

        return message;
    }
}
