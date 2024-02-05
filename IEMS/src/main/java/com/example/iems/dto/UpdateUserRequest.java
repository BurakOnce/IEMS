package com.example.iems.dto;

import com.example.iems.model.Role;
import lombok.*;

import java.util.Set;


@Builder
@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String username;
    private String password;
    private Long salary;
    private String town;
    private String city;
    private Set<Role> authorities;

}

