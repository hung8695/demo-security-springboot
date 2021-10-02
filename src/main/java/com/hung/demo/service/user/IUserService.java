package com.hung.demo.service.user;

import com.hung.demo.dto.request.UserRegisterForm;
import com.hung.demo.model.User;
import com.hung.demo.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends IGeneralService <User>, UserDetailsService {
    Optional<User> findByEmail(String email); //Tim kiem email co ton tai trong DB khong?
    Boolean existsByEmail(String email); //email da co trong DB chua?

    User register(UserRegisterForm registerForm);
}
