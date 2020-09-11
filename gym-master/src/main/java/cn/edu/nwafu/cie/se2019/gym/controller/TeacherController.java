package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.payload.users.ResetPasswordRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.UpdatePasswordRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.ListTeacherResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.NewTeacherRequest;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.TeacherInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.UpdateTeacherInfoRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import cn.edu.nwafu.cie.se2019.gym.service.TeacherService;
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
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherService teacherService;

    @GetMapping({"/myInformation", "/my"})
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> myInformation(@CurrentUser TeacherPrincipal currentUser) {
        return teacherInformationForAdmin(currentUser.getId());
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> teacherInformationForAdmin(@PathVariable Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isEmpty())
            return ResponseEntity.notFound().build();
        Teacher teacher = optionalTeacher.get();
        TeacherInformationResponse tir = new TeacherInformationResponse();
        tir.setId(teacher.getTid());
        tir.setName(teacher.getTname());
        tir.setGender(teacher.getTgender());
        tir.setEmail(teacher.getTmail());
        tir.setTel(teacher.getTtel());
        return ResponseEntity.ok(tir);
    }

    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listTeachersForAdmin() {
        ListTeacherResponse resp = new ListTeacherResponse();
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherInformationResponse> teacherResponses = new ArrayList<>();
        for (Teacher t : teachers) {
            teacherResponses.add(new TeacherInformationResponse(t));
        }
        resp.setTeachers(teacherResponses);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/forGuest")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> listTeachersForGuest() {
        ListTeacherResponse resp = new ListTeacherResponse();
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherInformationResponse> teacherResponses = new ArrayList<>();
        for (Teacher t : teachers) {
            TeacherInformationResponse tr = new TeacherInformationResponse(t);
            tr.setEmail(null);
            tr.setTel(null);
            teacherResponses.add(tr);
        }
        resp.setTeachers(teacherResponses);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> teacherInformation(@PathVariable Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isEmpty())
            return ResponseEntity.notFound().build();
        Teacher teacher = optionalTeacher.get();
        TeacherInformationResponse tir = new TeacherInformationResponse(teacher);
        tir.setEmail(null);
        tir.setTel(null);
        return ResponseEntity.ok(tir);
    }

    @PostMapping({"/updateInformation", "/my"})
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateInformation(@CurrentUser TeacherPrincipal currentUser,
                                               @Valid @RequestBody UpdateTeacherInfoRequest req) {
        return updateInformationForAdmin(currentUser.getId(), req);
    }

    @PostMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateInformationForAdmin(@PathVariable long id,
                                                       @Valid @RequestBody UpdateTeacherInfoRequest req) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isEmpty())
            return ResponseEntity.notFound().build();
        Teacher teacher = optionalTeacher.get();
        if (req.getName() != null)
            teacher.setTname(req.getName());
        if (req.getEmail() != null)
            teacher.setTmail(req.getEmail());
        if (req.getTel() != null)
            teacher.setTtel(req.getTel());
        if (req.getGender() != null)
            teacher.setTgender(req.getGender());
        teacherRepository.saveAndFlush(teacher);
        return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
    }


    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updatePassword(@CurrentUser TeacherPrincipal currentUser,
                                            @Valid @RequestBody UpdatePasswordRequest req) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(currentUser.getId());
        if (optionalTeacher.isEmpty())
            return ResponseEntity.notFound().build();
        Teacher teacher = optionalTeacher.get();
        try {
            if (!Pbkdf2WithHmacSHA512.validatePasswordhash(req.getCurrentPassword(), teacher.getTpwd()))
                return ResponseEntity.status(401).body(
                        Map.of("code", "-1", "message", "Current password incorrect"));
            return updatePasswordForAdmin(teacher.getTid(), req);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePasswordForAdmin(@PathVariable long id, @RequestBody UpdatePasswordRequest req) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (optionalTeacher.isEmpty())
            return ResponseEntity.notFound().build();
        Teacher teacher = optionalTeacher.get();
        try {
            if (req.getNewPassword() == null || req.getNewPassword().length() < 6)
                return ResponseEntity.status(401).body(
                        Map.of("code", "-2", "message", "New password not set or too short"));
            teacher.setTpwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getNewPassword()));
            teacherRepository.saveAndFlush(teacher);
            return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

    @PutMapping("/addNewTeacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> newTeacherForAdmin(@Valid @RequestBody NewTeacherRequest req) {
        if (teacherRepository.existsByTmail(req.getEmail()))
            return ResponseEntity.status(409).body(Map.of("code", "-2", "message", "邮箱重复。"));
        if (teacherRepository.existsByTtel(req.getTel()))
            return ResponseEntity.status(409).body(Map.of("code", "-3", "message", "电话重复。"));
        if (req.getInitialPassword() == null || req.getInitialPassword().length() < 6)
            return ResponseEntity.status(400).body(Map.of("code", "-4", "message", "密码太短。"));
        Teacher teacher = new Teacher();
        teacher.setTname(req.getName());
        teacher.setTtel(req.getTel());
        teacher.setTmail(req.getEmail());
        teacher.setTgender(req.getGender());
        try {
            teacher.setTpwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getInitialPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return ResponseEntity.status(500).body(Map.of("code", "-101", "message", "服务内部错误"));
        }
        teacherRepository.saveAndFlush(teacher);
        return ResponseEntity.ok(Map.of("code", "0", "message", "添加成功"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        Optional<Teacher> optionalTeacher = teacherRepository.findByTtel(req.getTel());
        if (optionalTeacher.isEmpty())
            return ResponseEntity.notFound().build();
        if(!optionalTeacher.get().getTmail().equals(req.getEmail()))
            return ResponseEntity.status(401).body(
                    Map.of("code", "-1", "message", "邮箱验证失败！"));
        if (req.getNewPassword().length() < 6)
            return ResponseEntity.status(400).body(Map.of("code", "-4", "message", "密码太短。"));
        Teacher teacher = optionalTeacher.get();
        try {
            teacher.setTpwd(Pbkdf2WithHmacSHA512.generateStrongPasswordHash(req.getNewPassword()));
            teacherRepository.saveAndFlush(teacher);
            return ResponseEntity.ok(Map.of("code", "0", "message", "Ok."));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(500).build();
    }

}
