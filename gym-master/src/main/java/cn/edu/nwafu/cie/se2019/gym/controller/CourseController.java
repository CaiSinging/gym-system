package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.Course;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.payload.course.ListCourseResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.course.NewCourseRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import cn.edu.nwafu.cie.se2019.gym.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping({"/api/courses", "/api/course"})
public class CourseController {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseService courseService;


    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listCoursesForAdmin() {
        ListCourseResponse resp = new ListCourseResponse();
        List<Course> courses = courseService.getAllCourse();
        List<ListCourseResponse.CourseResponse> courseResponses = new ArrayList<>();
        for (Course c : courses) {
            Optional<Teacher> ot = teacherRepository.findById(c.getTid());
            if (ot.isEmpty())
                continue;
            courseResponses.add(new ListCourseResponse.CourseResponse(c));
        }
        resp.setCourses(courseResponses);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/forGuest")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> listCoursesForGuest() {
        ListCourseResponse resp = new ListCourseResponse();
        List<Course> courses = courseService.getCanOrderCourse();
        List<ListCourseResponse.CourseResponse> courseResponses = new ArrayList<>();
        for (Course c : courses) {
            Optional<Teacher> ot = teacherRepository.findById(c.getTid());
            if (ot.isEmpty())
                continue;
            courseResponses.add(new ListCourseResponse.CourseResponse(c));
        }
        resp.setCourses(courseResponses);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/myCourses")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> listPhysicalTests(@CurrentUser TeacherPrincipal currentUser) {
        ListCourseResponse resp = new ListCourseResponse();
        List<Course> courses = courseRepository.findByTid(currentUser.getId());
        List<ListCourseResponse.CourseResponse> courseResponses = new ArrayList<>();
        for (Course c : courses) {
            Optional<Teacher> ot = teacherRepository.findById(c.getTid());
            if (ot.isEmpty())
                continue;
            courseResponses.add(new ListCourseResponse.CourseResponse(c));
        }
        resp.setCourses(courseResponses);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminDeleteCourse(@PathVariable long courseId) {
        Optional<Course> oc = courseRepository.findById(courseId);
        Course c;
        if (oc.isEmpty())
            return ResponseEntity.status(404).body(
                    Map.of("code", "-1", "message", "无此课程。"));
        c = oc.get();
        courseRepository.delete(c);
        courseRepository.flush();
        return ResponseEntity.ok(Map.of("code", "0", "message", "删除课程成功"));
    }

    @PutMapping("/addNewCourse")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewCourse(@Valid @RequestBody NewCourseRequest req) {
        if (courseRepository.existsByCnameAndTid(req.getCname(), req.getTid()))
            return ResponseEntity.status(409).body(Map.of("code", "-1", "message", "课程重复。"));
        if (req.getSelectedStartDate().compareTo(req.getSelectedEndDate()) > 0)
            return ResponseEntity.status(409).body(Map.of("code", "-1", "message", "结束时间应不早于开始时间。"));
        Course course = new Course();
        course.setCname(req.getCname());
        course.setTid(req.getTid());
        course.setCcapacity(req.getCcapacity());
        course.setCstart(req.getSelectedStartDate());
        course.setCfinish(req.getSelectedEndDate());
        course.setCtime(req.getCtime());
        courseRepository.saveAndFlush(course);
        return ResponseEntity.ok(Map.of("code", "0", "message", "添加成功"));
    }


    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCourseInformation(@PathVariable long id) {
        Optional<Course> oc = courseRepository.findById(id);
        if (oc.isEmpty())
            return ResponseEntity.notFound().build();
        ListCourseResponse.CourseResponse cr = new ListCourseResponse.CourseResponse(oc.get());
        return ResponseEntity.ok(cr);
    }

    @PostMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCourseInformation(@PathVariable long id,
                                                     @RequestBody NewCourseRequest req) {
        Optional<Course> oc = courseRepository.findById(id);
        if (oc.isEmpty())
            return ResponseEntity.notFound().build();
        if (req.getSelectedStartDate().compareTo(req.getSelectedEndDate()) > 0)
            return ResponseEntity.status(409).body(Map.of("code", "-1", "message", "结束时间应不早于开始时间。"));
        Course c = oc.get();
        if (req.getCname() != null)
            c.setCname(req.getCname());
        if (req.getTid() > 0)
            c.setTid(req.getTid());
        if (req.getCcapacity() > 0)
            c.setCcapacity(req.getCcapacity());
        if (req.getCtime() != null)
            c.setCtime(req.getCtime());
        if (req.getSelectedStartDate() != null)
            c.setCstart(req.getSelectedStartDate());
        if (req.getSelectedEndDate() != null)
            c.setCfinish(req.getSelectedEndDate());
        courseRepository.saveAndFlush(c);
        return ResponseEntity.ok(Map.of("code", "0", "message", "Ok"));
    }
}
