package com.hung.demo.model;

import com.hung.demo.constant.Constant;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String companyName;
    @NotBlank
    private String shortName;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    @Lob
    private String image;

    private String phone;

    private String companyCode;
    @NotBlank
    @Lob
    private String description;

    private String address;

    private int numberOfStaff;

    private String branch;

    private String linkGoogle;

    private String website;

    private Constant.RoleName role;

    private boolean enabled;

    private boolean recommended;

    public Company() {
    }

    public Company(String companyName, String shortName, String email, String password, String image, String phone, String companyCode, String description, String address, int numberOfStaff, String branch, String linkGoogle, String website, Constant.RoleName role) {
        this.companyName = companyName;
        this.shortName = shortName;
        this.email = email;
        this.password = password;
        this.image = image;
        this.phone = phone;
        this.companyCode = companyCode;
        this.description = description;
        this.address = address;
        this.numberOfStaff = numberOfStaff;
        this.branch = branch;
        this.linkGoogle = linkGoogle;
        this.website = website;
        this.role = role;
    }

    public Company(@NotBlank @Size(min = 3, max = 50) String name,
                   @NotBlank @Size(min = 3, max = 20) String shortName,
                   @NotBlank @Size(max = 50) @Email String email,
                   @NotBlank @Size(min = 6, max = 100) String encode,
                   @NotBlank String description) {
        this.companyName = name;
        this.shortName = shortName;
        this.email = email;
        this.password = encode;
        this.description = description;
    }
}
