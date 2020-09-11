package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.LeaveOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveOrderRepository extends JpaRepository<LeaveOrder, Long> {
    List<LeaveOrder> findByTid(Long Tid);
    List<LeaveOrder> findByLstate(String lstate);
}
