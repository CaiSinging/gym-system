package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.exception.OrderException;
import cn.edu.nwafu.cie.se2019.gym.model.TestOrder;
import cn.edu.nwafu.cie.se2019.gym.repository.TestOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TestOrderService {

    @Autowired
    TestOrderRepository testOrderRepository;

    public List<TestOrder> getAllTestOrder() {
        return testOrderRepository.findAll();
    }

    public List<TestOrder> getGuestTestOrder(Long gid) {
        return testOrderRepository.findByGid(gid);
    }

    public List<TestOrder> getTeacherTestOrder(Long tid) {
        return testOrderRepository.findByTid(tid);
    }


    public void addTestOrder(Long gid, Long tid, Date tstart) throws OrderException, ParseException {
        List<TestOrder> testOrderListByGuest = testOrderRepository.findByGid(gid);
        List<TestOrder> testOrderListByTeacher = testOrderRepository.findByTid(tid);
        for (TestOrder tog : testOrderListByGuest) {
            if ((tstart.getYear() == tog.getTstart().getYear()) &&
                    tstart.getMonth() == tog.getTstart().getMonth() &&
                    tstart.getDate() == tog.getTstart().getDate()) {
                throw new OrderException("一天只能预约一次体质测试，请勿重复预约！");
            }
        }
        for (TestOrder tot : testOrderListByTeacher) {
            if ((tstart.getYear() == tot.getTstart().getYear()) &&
                    tstart.getMonth() == tot.getTstart().getMonth() &&
                    tstart.getDate() == tot.getTstart().getDate() &&
                    tstart.getHours() == tot.getTstart().getHours()
            ) {
                throw new OrderException("该教练此时段已有预约！");
            }
        }
        tstart.setMinutes(0);
        TestOrder testOrder = new TestOrder(gid, tid, tstart);
        testOrderRepository.save(testOrder);
    }

    public void deleteTestOrder(Long tno) {
        Optional<TestOrder> optionalTestOrder = testOrderRepository.findById(tno);
        if (optionalTestOrder.isPresent()) {
            TestOrder testOrder = optionalTestOrder.get();
            testOrderRepository.delete(testOrder);
        }
    }
}
