package cn.edu.nwafu.cie.se2019.gym.payload.courseSelection;

import cn.edu.nwafu.cie.se2019.gym.model.Course;
import cn.edu.nwafu.cie.se2019.gym.model.CourseSelection;
import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.payload.course.ListCourseResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.users.guest.GuestInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.repository.CourseRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public class ListCourseSelectionResponse {
    @Component
    public static class CourseSelectionResponse extends CourseSelection {
        private static CourseRepository courseRepository;
        private static GuestRepository guestRepository;
        private static TeacherRepository teacherRepository;
        @JsonProperty
        @JsonIgnoreProperties({"tel", "email"})
        ListCourseResponse.CourseResponse course;
        @JsonProperty
        @JsonIgnoreProperties({"vip", "tel", "email", "vreg", "vfin"})
        GuestInformationResponse guest;

        public CourseSelectionResponse() {
        }

        public CourseSelectionResponse(CourseSelection cs) {
            setCno(cs.getCno());
            setGid(cs.getGid());
            setCid(cs.getCid());
            Optional<Guest> og = guestRepository.findById(cs.getGid());
            og.ifPresent(value -> guest = new GuestInformationResponse(value));
            Optional<Course> oc = courseRepository.findById(cs.getCid());
            oc.ifPresent(value -> course = new ListCourseResponse.CourseResponse(value));
        }

        @Autowired
        public void setCourseRepository(CourseRepository o) {
            courseRepository = o;
        }

        @Autowired
        public void setGuestRepository(GuestRepository o) {
            guestRepository = o;
        }

        @Autowired
        public void setTeacherRepository(TeacherRepository o) {
            teacherRepository = o;
        }
    }

    private List<CourseSelectionResponse> courseSelections;

    public List<CourseSelectionResponse> getCourseSelections() {
        return courseSelections;
    }

    public void setCourseSelections(List<CourseSelectionResponse> courseSelections) {
        this.courseSelections = courseSelections;
    }
}
