package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.payload.JwtAuthenticationResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.LoginRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.security.JwtTokenProvider;
import cn.edu.nwafu.cie.se2019.gym.security.token.AdminToken;
import cn.edu.nwafu.cie.se2019.gym.security.token.GuestToken;
import cn.edu.nwafu.cie.se2019.gym.security.token.TeacherToken;
import cn.edu.nwafu.cie.se2019.gym.util.Pbkdf2WithHmacSHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/guest_sign_in")
    public ResponseEntity<?> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication auth = new GuestToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/teacher_sign_in")
    public ResponseEntity<?> authenticateTeacher(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = new TeacherToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/admin_sign_in")
    public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = new AdminToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestParam String pwd) throws Exception {
        String hashedPwd = Pbkdf2WithHmacSHA512.generateStrongPasswordHash(pwd);
        return ResponseEntity.ok(hashedPwd);
    }
}
