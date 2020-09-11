package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.Course;
import cn.edu.nwafu.cie.se2019.gym.model.CourseSelection;
import cn.edu.nwafu.cie.se2019.gym.payload.courseSelection.ListCourseSelectionResponse;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseSelectionRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.GuestPrincipal;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/courseSelections")
public class CourseSelectionController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseSelectionRepository courseSelectionRepository;

    @GetMapping("/mySelections")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> listCourseSelectionsByGuest(@CurrentUser GuestPrincipal currentUser) {
        ListCourseSelectionResponse resp = new ListCourseSelectionResponse();
        List<CourseSelection> courseSelections = courseSelectionRepository.findByGid(currentUser.getId());
        List<ListCourseSelectionResponse.CourseSelectionResponse> courseSelectionResponses = new ArrayList<>();
        for (CourseSelection cs : courseSelections) {
            courseSelectionResponses.add(new ListCourseSelectionResponse.CourseSelectionResponse(cs));
        }
        resp.setCourseSelections(courseSelectionResponses);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/electCourse/{courseId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> guestElectCourse(@CurrentUser GuestPrincipal currentUser, @PathVariable long courseId) {
        if (courseSelectionRepository.existsAllByGidAndCid(currentUser.getId(), courseId))
            return ResponseEntity.status(409).body(Map.of("code", "-1", "message", "已经选过该课程。"));
        CourseSelection cs = new CourseSelection();
        cs.setCid(courseId);
        cs.setGid(currentUser.getId());
        courseSelectionRepository.saveAndFlush(cs);
        return ResponseEntity.ok(Map.of("code", "0", "message", "选课成功"));
    }

    @DeleteMapping("/cancelSelection/{courseSelectionId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> guestCancelSelection(@CurrentUser GuestPrincipal currentUser,
                                                  @PathVariable long courseSelectionId) {
        Optional<CourseSelection> ocs = courseSelectionRepository.findById(courseSelectionId);
        CourseSelection cs;
        if (ocs.isEmpty() || !Objects.equals((cs = ocs.get()).getGid(), currentUser.getId()))
            return ResponseEntity.status(404).body(
                    Map.of("code", "-1", "message", "无此选课记录或选课记录不属于您。"));
        courseSelectionRepository.delete(cs);
        courseSelectionRepository.flush();
        return ResponseEntity.ok(Map.of("code", "0", "message", "退课成功"));
    }

    @GetMapping("/findByCourse/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> listCourseSelectionsByCourse(@CurrentUser TeacherPrincipal currentUser, @PathVariable Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty())
            return ResponseEntity.notFound().build();
        if (!Objects.equals(optionalCourse.get().getTid(), currentUser.getId()))
            return ResponseEntity.status(403).build();
        ListCourseSelectionResponse resp = new ListCourseSelectionResponse();
        List<CourseSelection> courseSelections = courseSelectionRepository.findByCid(id);
        List<ListCourseSelectionResponse.CourseSelectionResponse> courseSelectionResponses = new ArrayList<>();
        for (CourseSelection cs : courseSelections) {
            courseSelectionResponses.add(new ListCourseSelectionResponse.CourseSelectionResponse(cs));
        }
        resp.setCourseSelections(courseSelectionResponses);
        return ResponseEntity.ok(resp);
    }
}
