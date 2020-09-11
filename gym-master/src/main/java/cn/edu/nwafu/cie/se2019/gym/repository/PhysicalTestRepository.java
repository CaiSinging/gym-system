package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.PhysicalTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicalTestRepository extends JpaRepository<PhysicalTest, Long> {
    List<PhysicalTest> findByGid(Long Gid);
    List<PhysicalTest> findByTid(Long Tid);
}

