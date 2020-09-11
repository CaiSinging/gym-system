package cn.edu.nwafu.cie.se2019.gym.service;

import cn.edu.nwafu.cie.se2019.gym.exception.ResourceExistedException;
import cn.edu.nwafu.cie.se2019.gym.exception.ResourceNotFoundException;
import cn.edu.nwafu.cie.se2019.gym.model.Admin;
import cn.edu.nwafu.cie.se2019.gym.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin getAdminInfo(Long aid) {
        Admin admin;
        Optional<Admin> optionalAdmin = adminRepository.findById(aid);
        if (optionalAdmin.isPresent()) {
            admin = optionalAdmin.get();
            return admin;
        }
        return null;
    }

    public void updateAdminInfo(Long aid, String aname, String atel, String amail) {
        Optional<Admin> optionalAdmin = adminRepository.findById(aid);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setAname(aname);
            admin.setAtel(atel);
            admin.setAmail(amail);
            adminRepository.save(admin);
        }
    }

    public void resetAdminPwd(String atel, String pwd) {
        Admin admin = adminRepository.findByAtel(atel)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "atel", atel));
        admin.setApwd(pwd);
        adminRepository.save(admin);
    }

}

