package cn.edu.nwafu.cie.se2019.gym.payload.course;

import cn.edu.nwafu.cie.se2019.gym.model.Course;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.TeacherInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseSelectionRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public class ListCourseResponse {

    @Component
    public static class CourseResponse extends Course {
        private static CourseSelectionRepository courseSelectionRepository;
        private static TeacherRepository teacherRepository;
        @JsonProperty
        @JsonIgnoreProperties({"tel", "email"})
        TeacherInformationResponse teacher;
        @JsonProperty
        long selections = -1;

        public CourseResponse() {
        }

        public CourseResponse(Course c) {
            setCid(c.getCid());
            setCname(c.getCname());
            setTid(c.getTid());
            setCcapacity(c.getCcapacity());
            setCtime(c.getCtime());
            setCstart(c.getCstart());
            setCfinish(c.getCfinish());
            Optional<Teacher> ot = teacherRepository.findById(c.getTid());
            ot.ifPresent(value -> teacher = new TeacherInformationResponse(value));
            selections = courseSelectionRepository.countAllByCid(getCid());
        }

        @Autowired
        public void setCourseSelectionRepository(CourseSelectionRepository o) {
            courseSelectionRepository = o;
        }

        @Autowired
        public void setTeacherRepository(TeacherRepository o) {
            teacherRepository = o;
        }
    }

    private List<CourseResponse> courses;

    public List<CourseResponse> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseResponse> courses) {
        this.courses = courses;
    }
}
