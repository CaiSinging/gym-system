package cn.edu.nwafu.cie.se2019.gym.payload.physicalTest;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.model.PhysicalTest;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.payload.users.guest.GuestInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.TeacherInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public class ListPhysicalTestResponse {
    @Component
    public static class PhysicalTestResponse extends PhysicalTest {
        private static GuestRepository guestRepository;
        private static TeacherRepository teacherRepository;
        @JsonProperty
        @JsonIgnoreProperties({"tel", "email"})
        TeacherInformationResponse teacher;
        @JsonProperty
        @JsonIgnoreProperties({"vip", "tel", "email", "vreg", "vfin"})
        GuestInformationResponse guest;

        public PhysicalTestResponse() {
        }

        public PhysicalTestResponse(PhysicalTest pt) {
            setPno(pt.getPno());
            setGid(pt.getGid());
            setTid(pt.getTid());
            setTime(pt.getTime());
            setFat(pt.getFat());
            setGripPower(pt.getGripPower());
            setHeight(pt.getHeight());
            setSitAndReach(pt.getSitAndReach());
            setSitUp(pt.getSitUp());
            setVitalCapacity(pt.getVitalCapacity());
            setWeight(pt.getWeight());
            Optional<Teacher> ot = teacherRepository.findById(pt.getTid());
            ot.ifPresent(value -> teacher = new TeacherInformationResponse(value));
            Optional<Guest> og = guestRepository.findById(pt.getGid());
            og.ifPresent(value -> guest = new GuestInformationResponse(value));
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

    private List<PhysicalTestResponse> physicalTests;

    public List<PhysicalTestResponse> getPhysicalTests() {
        return physicalTests;
    }

    public void setPhysicalTests(List<PhysicalTestResponse> physicalTests) {
        this.physicalTests = physicalTests;
    }
}
