package com.springboot.springlogin.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springboot.springlogin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.qos.logback.core.filter.Filter;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFiller extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse arg1, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                jwt = authorizationHeader.substring("Bearer ".length());
                username = jwtUtil.extractUsername(jwt);
            }
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userService.loadUserByUsername(username);
                if(jwtUtil.validateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        // } catch (ExpiredJwtException e) {
        //     String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
        //     String requestURL = httpServletRequest.getRequestURL().toString();

        //     if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
        //         allowForRefreshToken(e, httpServletRequest);
        //     }
        //     e.printStackTrace();
        // }
        filterChain.doFilter(httpServletRequest, arg1);
    }
}
