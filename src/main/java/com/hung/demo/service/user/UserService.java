package com.hung.demo.service.user;

import com.hung.demo.constant.Constant;
import com.hung.demo.dto.request.UserRegisterForm;
import com.hung.demo.model.User;
import com.hung.demo.repository.UserRepository;
import com.hung.demo.security.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User register(UserRegisterForm userRegisterForm) {
        User user = new User();
        user.setName(userRegisterForm.getName().trim());
        user.setEmail(userRegisterForm.getEmail().trim());
        String encode = passwordEncoder.encode(userRegisterForm.getPassword().trim());
        user.setPassword(encode);
        user.setPhone(userRegisterForm.getPhone());
        user.setRole(Constant.RoleName.USER);
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByEmail(username);
        if (!user.isPresent()) throw new UsernameNotFoundException(username);
        return UserPrincipal.build(user.get());
    }
}
