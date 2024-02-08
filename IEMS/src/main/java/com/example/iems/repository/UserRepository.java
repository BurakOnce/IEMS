package com.example.iems.repository;

import com.example.iems.model.Role;
import com.example.iems.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

    User findUsersByUsername(String Username);

    @Query("SELECT u.authorities FROM User u WHERE u.username = :username")
    List<String> findAuthoritiesByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a = :role")
    List<User> findUserByRole(@Param("role") Role role);

    List<User> findUsersByCity(String city);

    List<User> findUsersByTown(String town);

}
