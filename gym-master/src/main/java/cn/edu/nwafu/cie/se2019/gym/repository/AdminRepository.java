package cn.edu.nwafu.cie.se2019.gym.repository;

import cn.edu.nwafu.cie.se2019.gym.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
        Optional<Admin> findByAtel(String Atel);
        Optional<Admin> findByAmail(String amail);
        Boolean existsByAname(String Aname);
        Boolean existsByAtel(String Atel);
        Boolean existsByAmail(String Amail);
}
