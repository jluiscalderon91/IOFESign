package com.apsout.electronictestimony.api.config.security;

import com.apsout.electronictestimony.api.util.security.JWTTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.apsout.electronictestimony.api.util.statics.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private UserDetailsService userDetailsService;
    private JWTTokenUtil jwtTokenUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JWTTokenUtil jwtTokenUtil) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
        if (token != null) {
            if (jwtTokenUtil.validateToken(token)) {
                String username = jwtTokenUtil.getSubjectOf(token);
                if (username != null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (userDetails != null) {
                        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    }
                    return null;
                }
                return null;
            }
            return null;
        }
        return null;
    }
}
