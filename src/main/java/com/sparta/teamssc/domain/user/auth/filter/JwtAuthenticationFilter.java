package com.sparta.teamssc.domain.user.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.teamssc.domain.user.auth.dto.response.LoginResponseDto;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;

import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    /**
     * 요청 필터링: JWT 토큰을 검증해서 유효하면 사용자 정보 설정
     *
     * @param request  HTTP 요청
     * @param response HTTP 응답
     * @param chain    필터 체인
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청헤더에서 Authorization 추출
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = JwtUtil.getUsernameFromToken(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (JwtUtil.validateToken(jwt, userDetails)) {
                // 엑세스 토큰이 유효한 경우
                List<SimpleGrantedAuthority> authorities = JwtUtil.getRolesFromToken(jwt);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } else if (JwtUtil.isTokenExpired(jwt)) {
                // 엑세스 토큰이 만료된 경우 Json, Java 변환
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> requestBody = mapper.readValue(request.getInputStream(), Map.class);
                String refreshToken = requestBody.get("refreshToken");

                if (refreshToken != null && JwtUtil.validateRefreshToken(refreshToken)) {

                    // 리프레시 토큰이 유효한 경우 새로운 토큰 발급
                    LoginResponseDto newTokens = JwtUtil.refreshAccessToken(requestBody, userRepository);

                    response.setContentType("application/json");
                    response.getWriter().write(mapper.writeValueAsString(newTokens));
                } else {
                    // 리프레시 토큰도 만료된 경우 401 에러 반환
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 만료되었습니다. 재로그인이 필요합니다.");
                    return;

                }
            }
        }
        // 다음 필터로 요청을 전달
        chain.doFilter(request, response);
    }
}