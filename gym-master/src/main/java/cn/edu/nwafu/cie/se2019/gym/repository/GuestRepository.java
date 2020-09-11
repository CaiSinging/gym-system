package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByGtel(String Gtel);

    Optional<Guest> findByGmail(String email);

    Optional<Guest> findByGnameOrGmail(String name, String email);

    List<Guest> findByGidIn(List<Long> userIds);

    Optional<Guest> findByGname(String name);

    Boolean existsByGtel(String Gtel);
    Boolean existsByGmail(String Gmail);

    long countAllByGid(Long gid);
}
