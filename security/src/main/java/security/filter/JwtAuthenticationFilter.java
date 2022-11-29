// package security.filter;

// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;
// import security.service.JwtProvider;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtProvider jwtProvider;
//     private final UserDetailsService userDetailsService;

//     public JwtAuthenticationFilter(JwtProvider jwtProvider,
//                                    UserDetailsService userDetailsService) {
//         this.jwtProvider = jwtProvider;
//         this.userDetailsService = userDetailsService;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain) throws ServletException, IOException {

//         String jwt = getJwtFromRequest(request);

//         if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {

//             String email = jwtProvider.getEmailFromToken(jwt);
//             UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//             UsernamePasswordAuthenticationToken authentication =
//                     new UsernamePasswordAuthenticationToken(null, userDetails.getAuthorities());
//             authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//             SecurityContextHolder.getContext().setAuthentication(authentication);
//         }
//         filterChain.doFilter(request, response);
//     }

//     private String getJwtFromRequest(HttpServletRequest request) {

//         String bearerToken = request.getHeader("Authorization");

//         if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//             return bearerToken.substring(7);
//         }
//         return bearerToken;
//     }
// }
