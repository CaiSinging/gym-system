package cn.edu.nwafu.cie.se2019.gym.security;

import cn.edu.nwafu.cie.se2019.gym.repository.AdminRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.token.AdminToken;
import cn.edu.nwafu.cie.se2019.gym.security.token.GuestToken;
import cn.edu.nwafu.cie.se2019.gym.security.token.TeacherToken;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    AdminRepository adminRepository;
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiration}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getPrincipal() + "";
        Long userId = null;
        if (authentication.getClass().equals(GuestToken.class))
            userId = guestRepository.findByGmail(username).get().getGid();
        else if (authentication.getClass().equals(TeacherToken.class))
            userId = (long) teacherRepository.findByTmail(username).get().getTid();
        else if (authentication.getClass().equals(AdminToken.class))
            userId = (long) adminRepository.findByAmail(username).get().getAid();
        else
            throw new UsernameNotFoundException("Invalid user.");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority ga : authorities)
            roles.add(ga.toString());
        return Jwts.builder()
                .compressWith(CompressionCodecs.GZIP)
                .setSubject(Long.toString(userId))
                .claim("role", String.join("|", roles))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public String[] getUserRoleFromJWT(String token) {
        String roles = (String) (Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("role"));
        return roles.split("\\|");
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
