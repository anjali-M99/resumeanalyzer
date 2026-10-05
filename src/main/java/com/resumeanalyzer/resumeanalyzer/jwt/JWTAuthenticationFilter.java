package com.resumeanalyzer.resumeanalyzer.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.resumeanalyzer.resumeanalyzer.constants.Constants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@Order(1)
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger Log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	private JWTHelper jwtHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getRequestURI().endsWith(Constants.ADMIN_URI) ||
				request.getRequestURI().endsWith(Constants.ADMIN_ANALYSIS_URI)){
			Log.info("Inside auth filter");
			String authHeader = request.getHeader(Constants.AUTHORIZATION);
			String username = null;
			String jwt = null;

			if (authHeader != null && authHeader.startsWith(Constants.BEARER)) {
				jwt = authHeader.substring(7);
				username = jwtHelper.extractUsername(jwt);
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				if (jwtHelper.validateToken(jwt, username)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							null, null);
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		} else {
			Log.info("not needed security check");
		}
		filterChain.doFilter(request, response);
		
	}

}
