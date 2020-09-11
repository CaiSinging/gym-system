package cn.edu.nwafu.cie.se2019.gym.security.provider;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.token.TeacherToken;
import cn.edu.nwafu.cie.se2019.gym.util.Pbkdf2WithHmacSHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Component
public class TeacherAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<Teacher> user = teacherRepository.findByTmail(email);
        if (user.isEmpty()) {
            throw new BadCredentialsException("USER_NOT_EXISTS_OR_PASSWORD_INCORRECT");
        }
        try {
            if (!Pbkdf2WithHmacSHA512.validatePasswordhash(password, user.get().getTpwd())) {
                throw new BadCredentialsException("USER_NOT_EXISTS_OR_PASSWORD_INCORRECT");
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new BadCredentialsException("CORRUPTED_DATA");
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(TeacherToken.class);
    }
}
