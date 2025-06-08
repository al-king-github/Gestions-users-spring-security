package sn.estm.jwtTokenUtils;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter  extends OncePerRequestFilter{
    //	private JwtTokenUtil jwtTokens;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException,IOException
    {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Impossible de récupérer le jeton JWT");
            } catch (ExpiredJwtException e) {
                System.out.println("Jeton JWT expiré");
            }
        } else {
            logger.warn("Il ne s'agit pas d'une authentification Bearer");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() ==
                null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Si le token est valide, configurez l'authentification
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                var principalUser = new UsernamePasswordAuthenticationToken(userDetails
                        , null, userDetails.getAuthorities());
                principalUser.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                        request));
                SecurityContextHolder.getContext().setAuthentication(principalUser);
            }
        }
        filterChain.doFilter(request, response);

    }

}