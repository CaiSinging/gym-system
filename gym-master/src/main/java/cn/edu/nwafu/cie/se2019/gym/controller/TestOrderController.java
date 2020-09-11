package cn.edu.nwafu.cie.se2019.gym.controller;

import cn.edu.nwafu.cie.se2019.gym.exception.OrderException;
import cn.edu.nwafu.cie.se2019.gym.model.TestOrder;
import cn.edu.nwafu.cie.se2019.gym.payload.testOrder.ListTestOrderResponse;
import cn.edu.nwafu.cie.se2019.gym.payload.testOrder.NewTestRequest;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.repository.TestOrderRepository;
import cn.edu.nwafu.cie.se2019.gym.security.CurrentUser;
import cn.edu.nwafu.cie.se2019.gym.security.principal.GuestPrincipal;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import cn.edu.nwafu.cie.se2019.gym.service.TestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/testOrders")
public class TestOrderController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    TestOrderService testOrderService;
    @Autowired
    TestOrderRepository testOrderRepository;

    @GetMapping("/myTestOrders")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> listTestOrdersForGuest(@CurrentUser GuestPrincipal currentUser) {
        ListTestOrderResponse resp = new ListTestOrderResponse();
        List<TestOrder> testOrders = testOrderService.getGuestTestOrder(currentUser.getId());
        List<ListTestOrderResponse.TestOrderResponse> testOrderResponses = new ArrayList<>();
        for (TestOrder to : testOrders)
            testOrderResponses.add(new ListTestOrderResponse.TestOrderResponse(to));
        resp.setTestOrders(testOrderResponses);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/myTestVips")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> listTestOrdersForTeacher(@CurrentUser TeacherPrincipal currentUser) {
        ListTestOrderResponse resp = new ListTestOrderResponse();
        List<TestOrder> testOrders = testOrderRepository.findByTid(currentUser.getId());
        List<ListTestOrderResponse.TestOrderResponse> testOrderResponses = new ArrayList<>();
        for (TestOrder to : testOrders)
            testOrderResponses.add(new ListTestOrderResponse.TestOrderResponse(to));
        resp.setTestOrders(testOrderResponses);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/orderTest/{teacherId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> putInRequestForTest(@CurrentUser GuestPrincipal currentUser,
                                                 @PathVariable long teacherId,
                                                 @Valid @RequestBody NewTestRequest req) {
        if (req.getSelectedStartDate().compareTo(new Date()) <= 0)
            return ResponseEntity.status(400).body(Map.of("code", "-1", "message", "预约时间不应早于当前时间。"));
        try {
            testOrderService.addTestOrder(currentUser.getId(), teacherId, req.getSelectedStartDate());
        } catch (OrderException | ParseException oe) {
            return ResponseEntity.status(400).body(Map.of("code", "-1", "message", oe.getMessage()));
        }
        return ResponseEntity.ok(Map.of("code", "0", "message", "体质测试预约成功。"));
    }

    @DeleteMapping("/cancelTestOrder/{testOrderId}")
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<?> guestCancelOrder(@CurrentUser GuestPrincipal currentUser,
                                              @PathVariable long testOrderId) {
        Optional<TestOrder> oto = testOrderRepository.findById(testOrderId);
        TestOrder to;
        if (oto.isEmpty() || !Objects.equals((to = oto.get()).getGid(), currentUser.getId()))
            return ResponseEntity.status(404).body(
                    Map.of("code", "-1", "message", "无此预约记录或预约记录不属于您。"));
        testOrderRepository.delete(to);
        testOrderRepository.flush();
        return ResponseEntity.ok(Map.of("code", "0", "message", "取消预约成功"));
    }
}
