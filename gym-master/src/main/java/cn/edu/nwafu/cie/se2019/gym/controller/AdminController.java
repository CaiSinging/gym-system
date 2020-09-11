package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.Admin;
import cn.edu.nwafu.cie.se2019.gym.payload.users.ResetPasswordRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.admin.AdminInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.users.admin.UpdateAdminInfoRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.UpdatePasswordRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.AdminRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.AdminPrincipal;
import cn.edu.nwafu.cie.se2019.gym.util.Pbkdf2WithHmacSHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping({"/myInformation", "/my"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> myInformation(@CurrentUser AdminPrincipal currentUser) {
        Optional<Admin> optionalAdmin = adminRepository.findById(currentUser.getId());
        if (optionalAdmin.isEmpty())
            return ResponseEntity.notFound().build();
        Admin admin = optionalAdmin.get();
        AdminInformationResponse air = new AdminInformationResponse();
        air.setId(admin.getAid());
        air.setName(admin.getAname());
        air.setEmail(admin.getAmail());
        air.setTel(admin.getAtel());
        return ResponseEntity.ok(air);
    }

    @PostMapping({"/updateInformation", "/my"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateInformation(@CurrentUser AdminPrincipal currentUser,
                                               @Valid @RequestBody UpdateAdminInfoRequest req) {
        Optional<Admin> optionalAdmin = adminRepository.findById(currentUser.getId());
        if (optionalAdmin.isEmpty())
            return ResponseEntity.notFound().build();
        Admin admin = optionalAdmin.get();
        if (req.getName() != null)
            admin.setAname(req.getName());
        if (req.getEmail() != null)
            admin.setAmail(req.getEmail());
        if (req.getTel() != null)
            admin.setAtel(req.getTel());
        adminRepository.saveAndFlush(admin);
        return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
    }

    @PostMapping({"/updatePassword", "/my/password"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePassword(@CurrentUser AdminPrincipal currentUser,
                                            @Valid @RequestBody UpdatePasswordRequest req) {
        Optional<Admin> optionalAdmin = adminRepository.findById(currentUser.getId());
        if (optionalAdmin.isEmpty())
            return ResponseEntity.notFound().build();
        Admin admin = optionalAdmin.get();
        try {
            if (!Pbkdf2WithHmacSHA512.validatePasswordhash(req.getCurrentPassword(), admin.getApwd()))
                return ResponseEntity.status(401).body(
                        Map.of("code", "-1", "message", "Current password incorrect"));
            admin.setApwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getNewPassword()));
            adminRepository.saveAndFlush(admin);
            return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        Optional<Admin> optionalAdmin = adminRepository.findByAtel(req.getTel());
        if (optionalAdmin.isEmpty())
            return ResponseEntity.notFound().build();
        if(!optionalAdmin.get().getAmail().equals(req.getEmail()))
            return ResponseEntity.status(401).body(
                    Map.of("code", "-1", "message", "邮箱验证失败！"));
        if (req.getNewPassword().length() < 6)
            return ResponseEntity.status(400).body(Map.of("code", "-4", "message", "密码太短。"));
        Admin admin = optionalAdmin.get();
        try {
            admin.setApwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getNewPassword()));
            adminRepository.saveAndFlush(admin);
            return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

}
