package com.sales.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sales.service.impl.AuthServiceImpl;


public class AuthFilter extends OncePerRequestFilter {

	private String TOKEN_HEADER = "Authorization";
	@Autowired
	private AuthServiceImpl userService;
	@Autowired
	private TokenUtils tokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader(TOKEN_HEADER);
		final SecurityContext securityContext = SecurityContextHolder.getContext();

		if (header != null && securityContext.getAuthentication() == null) {
			String username = tokenUtil.getUserNameFromToken(header);
			if (username != null) {
				UserDetails userDetails = userService.loadUserByUsername(username);
				if (tokenUtil.isTokenValid(header, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}
