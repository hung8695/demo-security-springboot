package com.hung.demo.controller;

import com.hung.demo.constant.Constant;
import com.hung.demo.dto.request.LoginForm;
import com.hung.demo.dto.request.UserRegisterForm;
import com.hung.demo.dto.response.JwtResponse;
import com.hung.demo.dto.response.Response;
import com.hung.demo.dto.response.ResponseBody;
import com.hung.demo.model.Admin;
import com.hung.demo.model.User;
import com.hung.demo.security.jwt.AdminJwtService;
import com.hung.demo.security.jwt.UserJwtService;
import com.hung.demo.security.principal.AdminPrincipal;
import com.hung.demo.security.principal.UserPrincipal;
import com.hung.demo.service.admin.IAdminService;
import com.hung.demo.service.email.EmailService;
import com.hung.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserJwtService userJwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    AdminJwtService adminJwtService;
    @Autowired
    private IAdminService adminService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Validated @RequestBody UserRegisterForm userRegisterForm, BindingResult bindingResult, HttpServletRequest request) throws Exception{
    try{
        if(bindingResult.hasFieldErrors()){
          return new ResponseEntity<>(new ResponseBody(Response.OBJECT_INVALID, null), HttpStatus.BAD_REQUEST);
        }if (userService.existsByEmail(userRegisterForm.getEmail())){
            return new ResponseEntity<>(new ResponseBody(Response.EMAIL_IS_EXISTS,null), HttpStatus.CONFLICT);
        }
        User user = userService.register(userRegisterForm);
        if (user!=null){
            emailService.sendVerificationEmail(user);
        }
        return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, user), HttpStatus.CREATED);
    } catch (Exception e){
        return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR );
    }
    }

    @PostMapping("/users/sign-in")
    public ResponseEntity<?> userLogin(@Validated @RequestBody LoginForm loginForm) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (emailService.getType(loginForm.getEmail()).equals(Constant.RoleName.ADMIN.toString())) {
                String jwt = adminJwtService.generateTokenLogin(authentication);
                AdminPrincipal adminPrinciple = (AdminPrincipal) authentication.getPrincipal();
                Admin currentAdmin = adminService.findByEmail(loginForm.getEmail()).get();
                return new ResponseEntity<>(new ResponseBody(Response.SUCCESS,
                        new JwtResponse(currentAdmin.getId(), jwt, currentAdmin.getName(), adminPrinciple.getAuthorities())),
                        HttpStatus.OK);
            } else {
                String jwt = userJwtService.generateTokenLogin(authentication);
                UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
                User currentUser = userService.findByEmail(loginForm.getEmail()).get();
                return new ResponseEntity<>(new ResponseBody(Response.SUCCESS,
                        new JwtResponse(currentUser.getId(), jwt, currentUser.getName(), userPrinciple.getAuthorities())),
                        HttpStatus.OK);
            }
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.FORBIDDEN);
        }
    }
}

