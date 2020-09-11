package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.model.Course;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Course getCourseInfo(Long cid) {
        Course course;
        Optional<Course> optionalCourse = courseRepository.findById(cid);
        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
            return course;
        }
        return null;
    }

    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    public void updateCourseInfo(Long cid, String cname, Long tid, int ccapacity, String ctime, Date cstart, Date cfinish) {
        Optional<Course> optionalCourse = courseRepository.findById(cid);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setCname(cname);
            course.setTid(tid);
            course.setCcapacity(ccapacity);
            course.setCtime(ctime);
            course.setCstart(cstart);
            course.setCfinish(cfinish);
            courseRepository.save(course);
        }
    }

    public void addCourse(String cname, Long tid, int ccapacity, String ctime, Date cstart, Date cfinish) {
        Course course = new Course(cname, tid, ccapacity, ctime, cstart, cfinish);
        courseRepository.save(course);
    }

    public void deleteCourse(Long cid) {
        Optional<Course> optionalCourse = courseRepository.findById(cid);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            courseRepository.delete(course);
        }
    }

    public List<Course> getCanOrderCourse() {
        return courseRepository.findByCfinishGreaterThan(new Date());
    }
}
