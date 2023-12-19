package com.crackbyte.config;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    public CustomAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {

            String substring = token.substring(7);
            Claims claims = new JwtProcessor().getAllClaimsFromToken(substring);
            List<String> list = (List<String>) claims.get("authorities");
            log.info("authorities: {}", list);
            System.out.println(list);
//            User.UserBuilder userBuilder = User.builder()
//                .username("John Doe")
//                .authorities(list.toArray(new String[0]))
//                .password("password")
//                .accountExpired(false) // Set true if the account is expired
//                .accountLocked(false) // Set true if the account is locked
//                .credentialsExpired(false);
//            UserDetails userDetails = userDetailsrBuilder.build();
            return new UsernamePasswordAuthenticationToken("name", null,  list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
        return null;
    }

}
