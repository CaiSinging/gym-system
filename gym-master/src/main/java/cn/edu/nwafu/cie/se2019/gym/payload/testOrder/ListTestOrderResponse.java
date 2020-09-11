package cn.edu.nwafu.cie.se2019.gym.payload.testOrder;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.model.TestOrder;
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

public class ListTestOrderResponse {
    @Component
    public static class TestOrderResponse extends TestOrder {
        private static GuestRepository guestRepository;
        private static TeacherRepository teacherRepository;
        @JsonProperty
        @JsonIgnoreProperties({"tel", "email"})
        TeacherInformationResponse teacher;
        @JsonProperty
        @JsonIgnoreProperties({"vip", "tel", "email", "vreg", "vfin"})
        GuestInformationResponse guest;

        public TestOrderResponse() {
        }

        public TestOrderResponse(TestOrder to) {
            setTno(to.getTno());
            setGid(to.getGid());
            setTid(to.getTid());
            setTstart(to.getTstart());
            Optional<Teacher> ot = teacherRepository.findById(to.getTid());
            ot.ifPresent(value -> teacher = new TeacherInformationResponse(value));
            Optional<Guest> og = guestRepository.findById(to.getGid());
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

    private List<TestOrderResponse> testOrders;

    public List<TestOrderResponse> getTestOrders() {
        return testOrders;
    }

    public void setTestOrders(List<TestOrderResponse> testOrders) {
        this.testOrders = testOrders;
    }
}
