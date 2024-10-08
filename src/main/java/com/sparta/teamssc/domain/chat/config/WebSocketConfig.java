package com.sparta.teamssc.domain.chat.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${websocket.relay-host}")
    private String relayHost;

    @Value("${websocket.relay-port}")
    private int relayPort;

    @Value("${websocket.client-id}")
    private String clientId;

    @Value("${websocket.client-password}")
    private String clientPassword;

    @Value("${websocket.virtual-host}")
    private String virtualHost;

    @Value("${websocket.endpoint}")
    private String webSocketEndpoint;

    @Value("${websocket.allowed-origins}")
    private String[] allowedOrigins;

    private final WebSocketAuthInterceptor webSocketAuthInterceptor; // 인증정보 보안 컨텍스트에 세팅
    private final WebSocketSecurityContextChannelInterceptor securityContextChannelInterceptor; // 보안 컨텍스트에 있는걸 쓰레드 컨텍트스 홀더에 셍팅 및 지춤


    // 메시지 브로커 app인걸 라우팅
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        log.debug("STOMP Broker Relay 설정 중");
        registry.setPathMatcher(new AntPathMatcher("/"));
        registry.enableStompBrokerRelay("/topic", "/queue", "/exchange", "/amq/queue", "/chat")
                .setRelayHost(relayHost)//("localhost")
                .setRelayPort(relayPort)
                .setClientLogin(clientId)
                .setClientPasscode(clientPassword)
                .setVirtualHost(virtualHost)
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(5000);
        registry.setApplicationDestinationPrefixes("/app");
    }

    // STOMP 엔드포인트를 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(webSocketEndpoint)
                .setAllowedOriginPatterns(allowedOrigins);        // .withSockJS();
    }

    // 클라이언트 인바운드 채널에 인터셉터 (인증정보랑 컨텍스트 홀더 세팅 관련 인터셉트)
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketAuthInterceptor, securityContextChannelInterceptor);
    }

    // 클라이언트 아웃바인드 채널에 인터셉터
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10); //스레드 풀 크기를 설정해서 병렬 처리
    }
}
