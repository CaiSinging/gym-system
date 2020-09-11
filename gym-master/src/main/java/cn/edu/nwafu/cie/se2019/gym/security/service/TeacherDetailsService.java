package cn.edu.nwafu.cie.se2019.gym.security.service;

import cn.edu.nwafu.cie.se2019.gym.exception.ResourceNotFoundException;
import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.repository.TeacherRepository;
import cn.edu.nwafu.cie.se2019.gym.security.principal.TeacherPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherDetailsService implements UserDetailsService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Teacher user = teacherRepository.findByTmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "email", email));

        return TeacherPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Teacher user = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));
        return TeacherPrincipal.create(user);
    }
}