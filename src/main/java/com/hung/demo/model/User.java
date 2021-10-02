package com.hung.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hung.demo.constant.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @Email
    private String email;
    private String phone;
    private Boolean enabled;
    private Constant.RoleName role;

    public User() {

    }

    public User(@NotBlank @Size(min = 3, max = 50) String name,
                @NotBlank @Size(max = 50) @Email String email,
                @NotBlank @Size(min = 6, max = 100) String encode,
                @Pattern(regexp = "^[+84]+[0-9]{9}$") String phone) {
        this.name = name;
        this.email = email;
        this.password = encode;
        this.phone = phone;
    }


}
