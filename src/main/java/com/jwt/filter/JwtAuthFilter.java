package com.jwt.filter;

import static com.jwt.exception.ErrorCode.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.config.SecurityConfig;
import com.jwt.exception.JwtAuthenticationException;
import com.jwt.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

	private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		// JWT 토큰 검증 예외 경로는 다음 필터로 넘김
		if(isAllowListed(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = extractToken(request);
		System.out.println("accessToken = " + accessToken);

		Authentication authentication = createAuthentication(accessToken);
		System.out.println("Authorities = " + authentication.getAuthorities());
		System.out.println("Is Authenticated = " + authentication.isAuthenticated());


		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("토큰 검증 완료");

		filterChain.doFilter(request, response);
	}

	private static Authentication createAuthentication(String accessToken) {
		try {
			Claims claims = JwtUtil.parseToken(accessToken);

			String userName = claims.getSubject();
			String role = claims.get("roles", String.class);

			List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
			System.out.println("권한 확인: " + authorities);

			return new TestingAuthenticationToken(userName, null, authorities);

		} catch (ExpiredJwtException e) {
			throw new JwtAuthenticationException(EXPIRED_TOKEN);
		} catch (JwtException e) {
			throw new JwtAuthenticationException(INVALID_TOKEN);
		}
	}

	private static boolean isAllowListed(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return Arrays.stream(SecurityConfig.AUTH_ALLOWLIST).anyMatch(url -> antPathMatcher.match(url, requestURI));
	}

	private String extractToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		System.out.println("authorizationHeader = '" + authorizationHeader + "'");


		if(StringUtils.isNotBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		} else {
			throw new JwtAuthenticationException(NOT_FOUND_TOKEN);
		}
	}
}
