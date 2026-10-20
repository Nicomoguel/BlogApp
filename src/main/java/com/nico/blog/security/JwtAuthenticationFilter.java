package com.nico.blog.security;

import com.nico.blog.sevices.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;



import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractToken(request); // we first extract the token
            if (token != null) {
                UserDetails userDetails = authenticationService.validateToken(token); // then validate the token

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( // create the authentication object
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                // we not add this to our security context
                // and this makes our authentication object available for the rest of the request
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (userDetails instanceof BlogUserDetails) {
                    request.setAttribute("userId", ((BlogUserDetails) userDetails).getId()); // if it is a BlogUserDetails, we set the id to look up by id instead of email
                }

            }
        } catch(Exception ex){ // catching any exceptions but not going to do anything about them
            // do not throw exceptions, just not authenticate the user
            log.warn("Received invalid auth token");
        }

        filterChain.doFilter(request, response); // this passes the request to the next filter in the chain

    }

    private String extractToken(HttpServletRequest request){
        // the token will be provided in the request with the prefix word brearer
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } return null;
    }

}
