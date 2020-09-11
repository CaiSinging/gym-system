package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByTtel(String Ttel);

    Optional<Teacher> findByTmail(String tmail);

    Boolean existsByTname(String Tname);

    Boolean existsByTtel(String Ttel);

    Boolean existsByTmail(String Tmail);

    long countAllByTid(Long tid);
}
