package com.example.iems.controller;


import com.example.iems.dto.AuthRequest;
import com.example.iems.dto.CreateUserRequest;
import com.example.iems.dto.UpdateUserRequest;
import com.example.iems.model.User;
import com.example.iems.service.JwtService;
import com.example.iems.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@Slf4j
public class UserController {

    private final UserService service;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public UserController(UserService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome Burak's App";
    }

    @PostMapping("/admin/addNewUser")
    public User addUser(@RequestBody CreateUserRequest request) {
        return service.createUser(request);
    }

    @PostMapping("/admin/updateUser")
    public User updateUser(String username , @RequestBody UpdateUserRequest request) {
        return service.updateUser(username,request);
    }

    @DeleteMapping("/admin/deleteUser")
    public ResponseEntity<String> deleteUser(String username) {
        service.deleteUser(username);
        return ResponseEntity.ok( "User has deleted from database");
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }
        log.info("invalid username " + request.username());
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    @GetMapping("/employee/info")
    public String getEmployeeString() {
        return "This is EMPLOYEE!";
    }

    @GetMapping("/admin/info")
    public String getAdminString() {
        return "This is ADMIN!";
    }

    @GetMapping("/manager/info")
    public String getManagerString() {
        return "This is MANAGER!";
    }

    @GetMapping("/admin/getUserByCity")
    public List<User> getUserByCity(String city) {return service.getUserByCity(city);}


}
