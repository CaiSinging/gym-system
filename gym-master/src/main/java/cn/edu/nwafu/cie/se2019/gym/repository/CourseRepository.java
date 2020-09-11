package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTid(Long tid);

    boolean existsByCnameAndTid(String cname, long tid);

    List<Course> findByCfinishGreaterThan(Date d);
}
