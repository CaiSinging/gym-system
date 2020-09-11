package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.ResetPasswordRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.UpdatePasswordRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.guest.*;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.GuestPrincipal;
import cn.edu.nwafu.cie.se2019.gym.util.Pbkdf2WithHmacSHA512;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/guest")
public class GuestController {
    @Autowired
    private GuestRepository guestRepository;

    @GetMapping({"/myInformation", "/my"})
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> myInformation(@CurrentUser GuestPrincipal currentUser) {
        return guestInformationForAdmin(currentUser.getId());
    }

    @PostMapping({"/updateInformation", "/my"})
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> updateInformation(@CurrentUser GuestPrincipal currentUser,
                                               @Valid @RequestBody UpdateGuestInfoRequest req) {
        return updateGuestInformationForAdmin(currentUser.getId(), req);
    }

    @PostMapping({"/updatePassword", "/my/password"})
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> updatePassword(@CurrentUser GuestPrincipal currentUser,
                                            @Valid @RequestBody UpdatePasswordRequest req) {
        Optional<Guest> optionalGuest = guestRepository.findById(currentUser.getId());
        if (optionalGuest.isEmpty())
            return ResponseEntity.notFound().build();
        Guest guest = optionalGuest.get();
        try {
            if (!Pbkdf2WithHmacSHA512.validatePasswordhash(req.getCurrentPassword(), guest.getGpwd()))
                return ResponseEntity.status(401).body(
                        Map.of("code", "-1", "message", "Current password incorrect"));
            return updateGuestPasswordForAdmin(guest.getGid(), req);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listGuests() {
        ListGuestResponse resp = new ListGuestResponse();
        List<Guest> guests = guestRepository.findAll();
        List<GuestInformationResponse> guestResponses = new ArrayList<>();
        for (Guest g : guests) {
            guestResponses.add(new GuestInformationResponse(g));
        }
        resp.setGuests(guestResponses);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> guestInformationForAdmin(@PathVariable Long id) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isEmpty())
            return ResponseEntity.notFound().build();
        Guest guest = optionalGuest.get();
        GuestInformationResponse cir = new GuestInformationResponse();
        cir.setId(guest.getGid());
        cir.setName(guest.getGname());
        cir.setEmail(guest.getGmail());
        cir.setGender(guest.getGgender());
        cir.setTel(guest.getGtel());
        cir.setVip(guest.isVIP());
        cir.setVreg(guest.getVreg());
        cir.setVfin(guest.getVfin());
        return ResponseEntity.ok(cir);
    }

    @PostMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateGuestInformationForAdmin(@PathVariable long id,
                                                            @Valid @RequestBody UpdateGuestInfoRequest req) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isEmpty())
            return ResponseEntity.notFound().build();
        Guest guest = optionalGuest.get();
        if (req.getName() != null)
            guest.setGname(req.getName());
        if (req.getEmail() != null)
            guest.setGmail(req.getEmail());
        if (req.getTel() != null)
            guest.setGtel(req.getTel());
        if (req.getGender() != null)
            guest.setGgender(req.getGender());
        guestRepository.saveAndFlush(guest);
        return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
    }

    @PostMapping("/{id}/service")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateGuestService(@PathVariable long id,
                                                @Valid @RequestBody UpdateGuestServiceRequest req){
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isEmpty())
            return ResponseEntity.notFound().build();
        Guest guest = optionalGuest.get();
        guest.setVIP(req.isVip());
        guest.setVreg(req.getVreg());
        guest.setVfin(req.getVfin());
        guestRepository.saveAndFlush(guest);
        return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
    }


    @PostMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateGuestPasswordForAdmin(@PathVariable long id,
                                                         @RequestBody UpdatePasswordRequest req) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isEmpty())
            return ResponseEntity.notFound().build();
        Guest guest = optionalGuest.get();
        try {
            if (req.getNewPassword() == null || req.getNewPassword().length() < 6)
                return ResponseEntity.status(401).body(
                        Map.of("code", "-2", "message", "New password not set or too short"));
            guest.setGpwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getNewPassword()));
            guestRepository.saveAndFlush(guest);
            return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }
    @PutMapping("/guestRegister")
    public ResponseEntity<?> guestRegister(@Valid @RequestBody NewGuestRequest req) {
        if (guestRepository.existsByGmail(req.getEmail()))
            return ResponseEntity.status(409).body(Map.of("code", "-2", "message", "邮箱重复。"));
        if (guestRepository.existsByGtel(req.getTel()))
            return ResponseEntity.status(409).body(Map.of("code", "-3", "message", "电话重复。"));
        if (req.getPassword() == null || req.getPassword().length() < 6)
            return ResponseEntity.status(400).body(Map.of("code", "-4", "message", "密码太短。"));
        Guest guest = new Guest();
        guest.setGname(req.getName());
        guest.setGgender(req.getGender());
        guest.setGtel(req.getTel());
        guest.setGmail(req.getEmail());
        guest.setGpwd(req.getPassword());

        try {
            guest.setGpwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return ResponseEntity.status(500).body(Map.of("code", "-101", "message", "服务内部错误"));
        }
        guestRepository.saveAndFlush(guest);
        return ResponseEntity.ok(Map.of("code", "0", "message", "注册成功"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        Optional<Guest> optionalGuest = guestRepository.findByGtel(req.getTel());
        if (optionalGuest.isEmpty())
            return ResponseEntity.status(400).body(
                    Map.of("code", "-1", "message", "无法根据给出的手机号码和邮箱找到用户。"));
        if(!optionalGuest.get().getGmail().equals(req.getEmail()))
            return ResponseEntity.status(400).body(
                    Map.of("code", "-1", "message", "无法根据给出的手机号码和邮箱找到用户。"));
        if (req.getNewPassword().length() < 6)
            return ResponseEntity.status(400).body(Map.of("code", "-4", "message", "密码太短。"));
        Guest guest = optionalGuest.get();
        try {
            guest.setGpwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getNewPassword()));
            guestRepository.saveAndFlush(guest);
            return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

}
