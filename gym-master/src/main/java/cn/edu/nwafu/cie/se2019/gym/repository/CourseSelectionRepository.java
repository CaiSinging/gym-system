package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSelectionRepository extends JpaRepository<CourseSelection, Long> {
    List<CourseSelection> findByGid(Long Gid);

    Long countAllByCid(Long cid);

    List<CourseSelection> findByCid(Long Cid);

    boolean existsAllByGidAndCid(Long gid, Long cid);
}
