package cn.edu.nwafu.cie.se2019.gym.security;

import cn.edu.nwafu.cie.se2019.gym.model.Teacher;
import cn.edu.nwafu.cie.se2019.gym.security.provider.AdminAuthenticationProvider;
import cn.edu.nwafu.cie.se2019.gym.security.provider.GuestAuthenticationProvider;
import cn.edu.nwafu.cie.se2019.gym.security.provider.TeacherAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import java.util.Arrays;
import java.util.List;

public class MyAuthenticationManager extends ProviderManager {

    public MyAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }
}
