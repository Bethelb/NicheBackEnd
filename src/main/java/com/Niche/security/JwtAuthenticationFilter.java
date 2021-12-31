package com.Niche.security;

import java.io.IOException; 

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Niche.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtUtils jwtUtils; 
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//		final String requestTokenHeader = request.getHeader("Authorization");
//		System.out.println(requestTokenHeader);
//		String username = null;
//		String jwtToken = null;
//		
//		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//			
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//				username = this.jwtUtils.extractUsername(jwtToken);
//				
//			} catch (ExpiredJwtException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				System.out.println("jwt token has expired"+e);
//			}catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				System.out.println("error"+e);
//			}
//			
//		}else {
//			System.out.println("Invalid token, not started with bearer string");
//		}
//		
//		//validate token
//		
//		if (username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
//			final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//			
//			if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
//				//token valid
//				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//				
//				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				
//				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//				
//			}
//			
//		}else {
//			System.out.println("Token is not valid");
//		}
//		
//		filterChain.doFilter(request, response);
//		
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		//Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}