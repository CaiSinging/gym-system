package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.TestOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestOrderRepository extends JpaRepository<TestOrder, Long> {
        List<TestOrder> findByGid(Long Gid);
        List<TestOrder> findByTid(Long Tid);
}
