package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.model.LeaveOrder;
import cn.edu.nwafu.cie.se2019.gym.payload.leaveOrder.ListLeaveOrderResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.leaveOrder.NewLeaveRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.AdminRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.LeaveOrderRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.AdminPrincipal;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import cn.edu.nwafu.cie.se2019.gym.service.LeaveOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/leaveOrders")
public class LeaveOrderController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    LeaveOrderRepository leaveOrderRepository;
    @Autowired
    LeaveOrderService leaveOrderService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listLeaveOrders() {
        ListLeaveOrderResponse resp = new ListLeaveOrderResponse();
        List<LeaveOrder> leaveOrders = leaveOrderService.getAllLeaveOrder();
        List<ListLeaveOrderResponse.LeaveOrderResponse> leaveOrderResponses = new ArrayList<>();
        for (LeaveOrder lo : leaveOrders)
            leaveOrderResponses.add(new ListLeaveOrderResponse.LeaveOrderResponse(lo));
        resp.setLeaveOrders(leaveOrderResponses);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/applyLeaves")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> putInRequestForLeave(@CurrentUser TeacherPrincipal currentUser,
                                                  @Valid @RequestBody NewLeaveRequest req) {
        if (req.getSelectedStartDate().compareTo(req.getSelectedEndDate()) > 0)
            return ResponseEntity.status(409).body(Map.of("code", "-1", "message", "结束时间应不早于开始时间。"));
        leaveOrderService.addLeaveOrder(currentUser.getId(), req.getSelectedStartDate(), req.getSelectedEndDate());
        return ResponseEntity.ok(Map.of("code", "0", "message", "请假申请提交成功。"));
    }

    @DeleteMapping("/cancelLeaveOrder/{leaveOrderId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> teacherCancelLeaveOrder(@CurrentUser TeacherPrincipal currentUser,
                                                     @PathVariable long leaveOrderId) {
        Optional<LeaveOrder> olo = leaveOrderRepository.findById(leaveOrderId);
        LeaveOrder lo;
        if (olo.isEmpty() || !Objects.equals((lo = olo.get()).getTid(), currentUser.getId()))
            return ResponseEntity.status(404).body(
                    Map.of("code", "-1", "message", "无此请假记录或请假记录不属于您。"));
        leaveOrderRepository.delete(lo);
        leaveOrderRepository.flush();
        return ResponseEntity.ok(Map.of("code", "0", "message", "销假成功"));
    }

    @GetMapping("/myLeaves")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> listLeaveOrdersByTeacher(@CurrentUser TeacherPrincipal currentUser) {
        ListLeaveOrderResponse resp = new ListLeaveOrderResponse();
        List<LeaveOrder> leaveOrders = leaveOrderService.getLeaveOrderByTid(currentUser.getId());
        List<ListLeaveOrderResponse.LeaveOrderResponse> leaveOrderResponses = new ArrayList<>();
        for (LeaveOrder lo : leaveOrders)
            leaveOrderResponses.add(new ListLeaveOrderResponse.LeaveOrderResponse(lo));
        resp.setLeaveOrders(leaveOrderResponses);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/leaveRequests/{leaveRequestId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveLeaveRequest(@CurrentUser AdminPrincipal currentUser, @PathVariable long leaveRequestId) {
        Optional<LeaveOrder> olo = leaveOrderRepository.findById(leaveRequestId);
        if (olo.isEmpty())
            return ResponseEntity.status(404).body(Map.of("code", "-1", "message", "此请假申请不存在。"));
        LeaveOrder lo = olo.get();
        if (!"待审批".equals(lo.getLstate()))
            return ResponseEntity.status(409).body(
                    Map.of("code", "-2", "message", "此请假申请已经被批准/拒绝。"));
        lo.setLstate("批准");
        lo.setAid(currentUser.getId());
        leaveOrderRepository.saveAndFlush(lo);
        return ResponseEntity.ok(Map.of("code", "0", "message", "批准成功。"));
    }

    @PostMapping("/leaveRequests/{leaveRequestId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectLeaveRequest(@CurrentUser AdminPrincipal currentUser, @PathVariable long leaveRequestId) {
        Optional<LeaveOrder> olo = leaveOrderRepository.findById(leaveRequestId);
        if (olo.isEmpty())
            return ResponseEntity.status(404).body(Map.of("code", "-1", "message", "此请假申请不存在。"));
        LeaveOrder lo = olo.get();
        if (!"待审批".equals(lo.getLstate()))
            return ResponseEntity.status(409).body(
                    Map.of("code", "-2", "message", "此请假申请已经被批准/拒绝。"));
        lo.setLstate("不批准");
        lo.setAid(currentUser.getId());
        leaveOrderRepository.saveAndFlush(lo);
        return ResponseEntity.ok(Map.of("code", "0", "message", "拒绝成功。"));
    }
}
