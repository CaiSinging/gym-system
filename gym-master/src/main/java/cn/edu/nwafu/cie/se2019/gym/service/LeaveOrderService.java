package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.model.LeaveOrder;
import cn.edu.nwafu.cie.se2019.gym.repository.LeaveOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveOrderService {

    @Autowired
    LeaveOrderRepository leaveOrderRepository;

    public List<LeaveOrder> getAllLeaveOrder() {
        return leaveOrderRepository.findAll();
    }

    public List<LeaveOrder> getLeaveOrderByTid(Long tid) {
        return leaveOrderRepository.findByTid(tid);
    }

    public List<LeaveOrder> getLeaveOrderByLstate(String lstate) {
        return leaveOrderRepository.findByLstate(lstate);
    }

    public void updateLeaveOrder(Long lno, Long tid, Date lstart, Date lend, Long aid, String lstate) throws ParseException {
        Optional<LeaveOrder> optionalLeaveOrder = leaveOrderRepository.findById(lno);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String lstartDate = df.format(lstart);
        String lendDate = df.format(lend);
        if (optionalLeaveOrder.isPresent()) {
            LeaveOrder leaveOrder = optionalLeaveOrder.get();
            leaveOrder.setTid(tid);
            leaveOrder.setLstart(df.parse(lstartDate));
            leaveOrder.setLend(df.parse(lendDate));
            leaveOrder.setAid(aid);
            leaveOrder.setLstate(lstate);
            leaveOrderRepository.saveAndFlush(leaveOrder);
        }
    }

    public void addLeaveOrder(Long tid, Date lstart, Date lend) {
        LeaveOrder leaveOrder = new LeaveOrder(tid, lstart, lend);
        leaveOrderRepository.saveAndFlush(leaveOrder);
    }

    public void deleteLeaveOrder(Long lno) {
        Optional<LeaveOrder> optionalLeaveOrder = leaveOrderRepository.findById(lno);
        if (optionalLeaveOrder.isPresent()) {
            LeaveOrder leaveOrder = optionalLeaveOrder.get();
            leaveOrderRepository.delete(leaveOrder);
        }
        leaveOrderRepository.flush();
    }

}
