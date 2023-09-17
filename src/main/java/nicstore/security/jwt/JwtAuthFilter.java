package nicstore.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import nicstore.security.services.UserDetailsImpl;
import nicstore.security.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isEmpty()) {
                response.sendError(response.SC_BAD_REQUEST, "Invalid JWT Token Bearer Header");
            } else {
                try {

                    String email = jwtUtil.validateTokenAndRetrieveToken(jwt);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    UserDetailsImpl userDetailsImpl = userDetailsServiceImpl.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetailsImpl,
                                    userDetailsImpl.getPassword(),
                                    userDetailsImpl.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException exc) {
                    response.sendError(response.SC_BAD_REQUEST, "Invalid JWT Token");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
