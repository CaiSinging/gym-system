package cn.edu.nwafu.cie.se2019.gym.security.service;

import cn.edu.nwafu.cie.se2019.gym.exception.ResourceNotFoundException;
import cn.edu.nwafu.cie.se2019.gym.model.Admin;
import cn.edu.nwafu.cie.se2019.gym.repository.AdminRepository;
import cn.edu.nwafu.cie.se2019.gym.security.principal.AdminPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Admin user = adminRepository.findByAmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "email", email));

        return AdminPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Admin user = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        return AdminPrincipal.create(user);
    }
}