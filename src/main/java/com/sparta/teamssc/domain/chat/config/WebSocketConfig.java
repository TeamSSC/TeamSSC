package com.sparta.teamssc.domain.chat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final WebSocketAuthInterceptor webSocketAuthInterceptor; // 인증정보 보안 컨텍스트에 세팅
    private final WebSocketSecurityContextChannelInterceptor securityContextChannelInterceptor; // 보안 컨텍스트에 있는걸 쓰레드 컨텍트스 홀더에 셍팅 및 지춤

    private final Map<String, Long> lastPongReceived = new ConcurrentHashMap<>();
    private static final long PONG_TIMEOUT = 30000; // 30초 동안 응답이 없으면 연결 끊김으로 간주


    // 메시지 브로커 app인걸 라우팅
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        log.debug("STOMP Broker Relay 설정 중");
        registry.setPathMatcher(new AntPathMatcher("/"));
        registry.enableStompBrokerRelay("/topic", "/queue", "/exchange", "/amq/queue","/chat")
                .setRelayHost("rabbitmq")//("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setVirtualHost("/")
                .setSystemHeartbeatSendInterval(10000)
                .setSystemHeartbeatReceiveInterval(10000);
        registry.setApplicationDestinationPrefixes("/app");
    }

    // STOMP 엔드포인트를 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
          registry.addEndpoint("/wss/init")
                .setAllowedOriginPatterns("*");        // .withSockJS();
    }

    // 클라이언트 인바운드 채널에 인터셉터 (인증정보랑 컨텍스트 홀더 세팅 관련 인터셉트)
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketAuthInterceptor, securityContextChannelInterceptor);

        // WebSocket 연결 종료 감지 핸들러
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
                    String sessionId = accessor.getSessionId();

                    // 원인을 정확히 알 수 없으므로, 단순 로그 남기고 클라이언트에 재연결 요청 가능
                    log.warn("WebSocket 연결 종료 감지 - 세션 ID: {}", sessionId);

                }
            }
        });
    }

    // 클라이언트 아웃바인드 채널에 인터셉터
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10); //스레드 풀 크기를 설정해서 병렬 처리
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                lastPongReceived.put(session.getId(), System.currentTimeMillis());
                sendPing(session); // Ping 메시지 전송 시작
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                if ("pong".equalsIgnoreCase(message.getPayload())) {
                    lastPongReceived.put(session.getId(), System.currentTimeMillis());
                    log.info("Pong 수신 - 세션 ID: {}", session.getId());
                }
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
                lastPongReceived.remove(session.getId());
                log.warn("WebSocket 연결 종료 - 세션 ID: {}", session.getId());
            }

            private void sendPing(WebSocketSession session) {
                new Thread(() -> {
                    while (session.isOpen()) {
                        try {
                            Thread.sleep(10000); // 10초마다 Ping 전송
                            session.sendMessage(new TextMessage("ping"));
                            checkPongTimeout(session);
                        } catch (Exception e) {
                            log.error("Ping 메시지 전송 오류", e);
                        }
                    }
                }).start();
            }

            private void checkPongTimeout(WebSocketSession session) {
                long lastPongTime = lastPongReceived.getOrDefault(session.getId(), 0L);
                if (System.currentTimeMillis() - lastPongTime > PONG_TIMEOUT) {
                    log.warn("Pong 응답 없음 - WebSocket 연결 종료: {}", session.getId());
                    try {
                        session.close(CloseStatus.GOING_AWAY);
                    } catch (Exception e) {
                        log.error("WebSocket 세션 종료 오류", e);
                    }
                }
            }
        };
    }
    }
