package com.ayush.auth_service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, 
                                 CustomUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
        try {
        	
        	// Skip token check for permitAll() endpoints
            if (request.getServletPath().startsWith("/auth/login") || 
                request.getServletPath().startsWith("/auth/register")) {
                filterChain.doFilter(request, response); // Proceed without token check
                return;
            }
        	
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt)) {
                if (jwtTokenUtil.validateTokenStructure(jwt)) {
                    String username = jwtTokenUtil.extractUsername(jwt);
                    
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    if (!jwtTokenUtil.validateToken(jwt, userDetails)) {
                        logger.warn("Invalid token for user: {}", username);
                        throw new BadCredentialsException("Invalid token");
                    }
                    
                    if (!userDetails.isEnabled()) {
                        logger.warn("User account is disabled: {}", username);
                        throw new DisabledException("User account is disabled");
                    }
                    
                    if (!userDetails.isAccountNonLocked()) {
                        logger.warn("User account is locked: {}", username);
                        throw new LockedException("User account is locked");
                    }
                    
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities());
                    authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.debug("Successfully authenticated user: {}", username);
                }
            }
        } catch (ExpiredJwtException ex) {
            handleException(response, "Token expired", HttpServletResponse.SC_UNAUTHORIZED, ex);
            return;
        } catch (@SuppressWarnings("deprecation") UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
            handleException(response, "Invalid token", HttpServletResponse.SC_UNAUTHORIZED, ex);
            return;
        } catch (IllegalArgumentException ex) {
            handleException(response, "JWT claims string is empty", HttpServletResponse.SC_UNAUTHORIZED, ex);
            return;
        } catch (DisabledException | LockedException | BadCredentialsException ex) {
            handleException(response, ex.getMessage(), HttpServletResponse.SC_FORBIDDEN, ex);
            return;
        } catch (Exception ex) {
            handleException(response, "Authentication failed", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex);
            return;
        }
        
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void handleException(HttpServletResponse response, String message, 
                               int status, Exception ex) throws IOException {
        logger.error("Authentication error: {}", ex.getMessage());
        SecurityContextHolder.clearContext();
        response.sendError(status, message);
    }
}
