package cn.edu.nwafu.cie.se2019.gym.security.service;
import cn.edu.nwafu.cie.se2019.gym.exception.ResourceNotFoundException;
import cn.edu.nwafu.cie.se2019.gym.model.Guest;
import cn.edu.nwafu.cie.se2019.gym.repository.GuestRepository;
import cn.edu.nwafu.cie.se2019.gym.security.principal.GuestPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GuestDetailsService implements UserDetailsService {

    @Autowired
    GuestRepository guestRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Guest user = guestRepository.findByGmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Customer not found with name or email : " + email)
                );

        return GuestPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Guest user = guestRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Guest", "id", id)
        );
        return GuestPrincipal.create(user);
    }
}