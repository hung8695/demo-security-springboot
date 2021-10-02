package com.hung.demo.dto.request;
import com.hung.demo.constant.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AdminRegisterForm {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
    private Constant.RoleName type;

    public AdminRegisterForm() {
    }

    public AdminRegisterForm(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
