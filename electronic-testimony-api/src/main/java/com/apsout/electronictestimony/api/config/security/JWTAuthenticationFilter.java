package com.apsout.electronictestimony.api.config.security;

import com.apsout.electronictestimony.api.entity.security.User;
import com.apsout.electronictestimony.api.entity.security.Userrole;
import com.apsout.electronictestimony.api.service.AuthenticationattemptService;
import com.apsout.electronictestimony.api.service.security.UserService;
import com.apsout.electronictestimony.api.service.security.UserroleService;
import com.apsout.electronictestimony.api.util.security.JWTTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.apsout.electronictestimony.api.util.statics.SecurityConstants.HEADER_STRING;
import static com.apsout.electronictestimony.api.util.statics.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTTokenUtil jwtTokenUtil;
    private UserService userService;
    private UserroleService userroleService;
    private AuthenticationattemptService authenticationattemptService;
    private User userEntity;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JWTTokenUtil jwtTokenUtil,
                                   UserService userService,
                                   UserroleService userroleService,
                                   AuthenticationattemptService authenticationattemptService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userroleService = userroleService;
        this.authenticationattemptService = authenticationattemptService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            userEntity = new ObjectMapper().readValue(req.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userEntity.getUsername(),
                userEntity.getPassword(),
                new ArrayList<>()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        String subject = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
        String token = jwtTokenUtil.buildBy(subject);
        User user = userService.getBy(subject);
        Userrole userrole = userroleService.getBy(user);
        Integer enterpriseId = user.getPersonByPersonId().getEnterpriseIdView();
        Integer personId = user.getPersonByPersonId().getId();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.addHeader("OnlyApi", String.valueOf(userrole.getOnlyApi()));
        response.addHeader("EnterpriseId", String.valueOf(enterpriseId));
        response.addHeader("PersonId", String.valueOf(personId));
    }

    /*@Override //Salvado de registros de autenticación incorrecta
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws MaxAuthenticationAttemptException {
        String username = userEntity.getUsername();
        Optional<User> optional = userService.findBy(username);
        if (optional.isPresent()) {
            User user = optional.get();
            Authenticationattempt authenticationattempt = AuthenticationattemptAllocator.build(user);
            authenticationattemptService.save(authenticationattempt);
            List<Authenticationattempt> attempts = authenticationattemptService.findLastBy(user, AuthenticationAttempt.MAX_ATTEMPTS);
            if (AuthenticationAttempt.MAX_ATTEMPTS == attempts.size()) {
                final int LAST_INDEX = attempts.size() - 1;
                Authenticationattempt first = attempts.get(AuthenticationAttempt.FIRST_INDEX);
                Authenticationattempt last = attempts.get(LAST_INDEX);
                Duration duration = Duration.ofMillis(AuthenticationAttempt.INTERVAL_VALIDITY);
                final Timestamp before = last.getLastAttemptAt();
                final Timestamp after = first.getLastAttemptAt();
                if (DateUtil.hasIntervalIn(before, after, duration)) {
                    throw new MaxAuthenticationAttemptException("Intentos excesivos.");
                }
            }
        }
        throw failed;
    }*/
}
