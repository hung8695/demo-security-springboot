package com.hung.demo.service.admin;

import com.hung.demo.model.Admin;
import com.hung.demo.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IAdminService extends IGeneralService<Admin> , UserDetailsService {
    Optional<Admin> findByEmail(String email); //Tim kiem email co ton tai trong DB khong?
    Boolean existsByEmail(String email); //email da co trong DB chua?
}
