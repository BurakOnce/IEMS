package com.example.iems.service;

import com.example.iems.dto.CreateUserRequest;
import com.example.iems.dto.UpdateUserRequest;
import com.example.iems.model.Role;
import com.example.iems.model.User;
import com.example.iems.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(CreateUserRequest request) {
        String username = request.username();
        if (userRepository.findByUsername(username).isPresent()) {
            return null;
        }

        User newUser = User.builder()
                .name(request.name())
                .username(username)
                .password(passwordEncoder.encode(request.password()))
                .salary(request.salary())
                .town(request.town())
                .city(request.city())
                .authorities(request.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        return userRepository.save(newUser);
    }

    public User updateUser(String username, UpdateUserRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            existingUser.setName(request.getName() != null ? request.getName() : existingUser.getName());
            existingUser.setUsername(request.getUsername() != null ? request.getUsername() : existingUser.getUsername());
            existingUser.setPassword(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : existingUser.getPassword());
            existingUser.setSalary(request.getSalary() != null ? request.getSalary() : existingUser.getSalary());
            existingUser.setTown(request.getTown() != null ? request.getTown() : existingUser.getTown());
            existingUser.setCity(request.getCity() != null ? request.getCity() : existingUser.getCity());
            existingUser.setAuthorities(request.getAuthorities() != null ? request.getAuthorities() : existingUser.getAuthorities());

            return userRepository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
    }

    public void deleteUser(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            userRepository.delete(existingUser);
            ResponseEntity.ok(userRepository.findAuthoritiesByUsername(username) + " has deleted from database");

        }else {
            ResponseEntity.ok(userRepository.findAuthoritiesByUsername(username) + " has not found in database");

        }
    }

    public List<User> getUserByCity(String city){
        return userRepository.findUsersByCity(city);
    }

    public List<User> getUserByRole(Role role) {
            return userRepository.findUserByRole(role);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }






}
