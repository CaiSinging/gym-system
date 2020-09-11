package cn.edu.nwafu.cie.se2019.gym.security;

import cn.edu.nwafu.cie.se2019.gym.security.service.AdminDetailsService;
import cn.edu.nwafu.cie.se2019.gym.security.service.GuestDetailsService;
import cn.edu.nwafu.cie.se2019.gym.security.service.TeacherDetailsService;
import io.jsonwebtoken.lang.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private GuestDetailsService guestDetailsService;

    @Autowired
    private TeacherDetailsService teacherDetailsService;
    @Autowired
    private AdminDetailsService adminDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                List roles = Collections.arrayToList(tokenProvider.getUserRoleFromJWT(jwt));
                UserDetails userDetails = null;
                if (roles.contains("ROLE_GUEST"))
                    userDetails = guestDetailsService.loadUserById(userId);
                else if (roles.contains("ROLE_TEACHER"))
                    userDetails = teacherDetailsService.loadUserById(userId);
                else if (roles.contains("ROLE_ADMIN"))
                    userDetails = adminDetailsService.loadUserById(userId);
                if (userDetails == null)
                    throw new Exception("Invalid role");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
