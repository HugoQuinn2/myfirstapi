package com.hq.myfirtsapi.jwt;

import com.hq.myfirtsapi.apiusers.AuthError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = getTokerFromRquest(request);
        final String username;

        if(token == null){
            filterChain.doFilter(request, response);
            if (request.getRequestURI().startsWith("/doc/")) {
                log.info(AuthError.PUBLIC_REQUEST.getMessage(
                        request.getRequestURL().toString(),
                        request.getRemoteAddr()));
            }
            return;
        }

        username = jwtService.getUserNameFromToken(token); //Tomar usario del token

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ // Si el usuario no esta en contente holder
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //Tomar usario de BD

            if(jwtService.isTokenValid(token, userDetails)){ //Validacion de token valido
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( //Actualizar el usuario en el contente holder
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //ceter el details
                SecurityContextHolder.getContext().setAuthentication(authToken); //cetear auntenticacion
            }else {
                log.info("The token " + token + "is not valid");
            }
        }

        filterChain.doFilter(request, response);
        log.info(AuthError.APPROVED_REQUEST.getMessage(
                request.getRequestURL().toString(),
                request.getRemoteAddr(),
                username));
    }

    public String getTokerFromRquest(HttpServletRequest request){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

}
