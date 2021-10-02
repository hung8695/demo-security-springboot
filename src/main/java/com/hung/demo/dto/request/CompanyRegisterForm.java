package com.hung.demo.dto.request;
import com.hung.demo.constant.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CompanyRegisterForm {
    @NotBlank
    @Size(min = 3, max = 50)
    private String companyName;
    @NotBlank
    @Size(min = 3, max = 20)
    private String shortName;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
    @NotBlank
    @Lob
    private String description;

    private Constant.RoleName type;

    public CompanyRegisterForm() {
    }

    public CompanyRegisterForm(String companyName, String shortName, String email, String password, String description) {
        this.companyName = companyName;
        this.shortName = shortName;
        this.email = email;
        this.password = password;
        this.description = description;
    }
}
