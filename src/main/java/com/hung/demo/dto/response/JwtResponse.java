package com.hung.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String name;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(Long id, String token, String name, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.token = token;
        this.name = name;
        this.roles = roles;
    }
}
