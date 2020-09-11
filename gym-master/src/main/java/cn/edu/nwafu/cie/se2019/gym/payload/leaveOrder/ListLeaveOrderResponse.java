package cn.edu.nwafu.cie.se2019.gym.payload.leaveOrder;

import cn.edu.nwafu.cie.se2019.gym.model.Admin;
import cn.edu.nwafu.cie.se2019.gym.model.LeaveOrder;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.payload.users.admin.AdminInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.users.teacher.TeacherInformationResponse;
import cn.edu.nwafu.cie.se2019.gym.repository.AdminRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public class ListLeaveOrderResponse {
    private List<LeaveOrderResponse> leaveOrders;

    public List<LeaveOrderResponse> getLeaveOrders() {
        return leaveOrders;
    }

    public void setLeaveOrders(List<LeaveOrderResponse> leaveOrders) {
        this.leaveOrders = leaveOrders;
    }

    @Component
    public static class LeaveOrderResponse extends LeaveOrder {
        private static AdminRepository adminRepository;
        private static TeacherRepository teacherRepository;
        @JsonProperty
        @JsonIgnoreProperties({"tel", "email"})
        TeacherInformationResponse teacher;
        @JsonProperty
        @JsonIgnoreProperties({"tel", "email"})
        AdminInformationResponse admin;

        public LeaveOrderResponse() {
        }

        public LeaveOrderResponse(LeaveOrder l) {
            setLno(l.getLno());
            setTid(l.getTid());
            setLstart(l.getLstart());
            setLend(l.getLend());
            setAid(l.getAid());
            setLstate(l.getLstate());
            Optional<Teacher> ot = teacherRepository.findById(l.getTid());
            ot.ifPresent(value -> teacher = new TeacherInformationResponse(value));
            Optional<Admin> oa = adminRepository.findById(l.getAid());
            oa.ifPresent(value -> admin = new AdminInformationResponse(value));
        }

        @Autowired
        public void setAdminRepository(AdminRepository o) {
            adminRepository = o;
        }

        @Autowired
        public void setTeacherRepository(TeacherRepository o) {
            teacherRepository = o;
        }
    }
}
