package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.PhysicalTest;
import cn.edu.nwafu.cie.se2019.gym.payload.physicalTest.ListPhysicalTestResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.physicalTest.NewPhysicalTestRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.PhysicalTestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.GuestPrincipal;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import cn.edu.nwafu.cie.se2019.gym.service.PhysicalTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/physicalTests")
public class PhysicalTestController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    PhysicalTestService physicalTestService;
    @Autowired
    PhysicalTestRepository physicalTestRepository;

    @GetMapping("/myTestReports")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> listPhysicalTests(@CurrentUser GuestPrincipal currentUser) {
        ListPhysicalTestResponse resp = new ListPhysicalTestResponse();
        List<PhysicalTest> physicalTests = physicalTestRepository.findByGid(currentUser.getId());
        List<ListPhysicalTestResponse.PhysicalTestResponse> physicalTestResponses = new ArrayList<>();
        for (PhysicalTest pt : physicalTests) {
            physicalTestResponses.add(new ListPhysicalTestResponse.PhysicalTestResponse(pt));
        }
        resp.setPhysicalTests(physicalTestResponses);
        return ResponseEntity.ok(resp);
    }
    @PutMapping("/addReport/{guestId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> addNewCourse(@CurrentUser TeacherPrincipal currentUser, @PathVariable long guestId, @Valid @RequestBody NewPhysicalTestRequest req) {
        PhysicalTest physicalTest = new PhysicalTest();
        physicalTest.setGid(guestId);
        physicalTest.setTid(currentUser.getId());
        physicalTest.setTime(new Date());
        physicalTest.setHeight(req.getHeight());
        physicalTest.setWeight(req.getWeight());
        physicalTest.setFat(req.getFat());
        physicalTest.setGripPower(req.getGripPower());
        physicalTest.setSitUp(req.getSitUp());
        physicalTest.setSitAndReach(req.getSitAndReach());
        physicalTest.setVitalCapacity(req.getVitalCapacity());
        physicalTestRepository.saveAndFlush(physicalTest);
        return ResponseEntity.ok(Map.of("code", "0", "message", "提交成功"));
    }
}
