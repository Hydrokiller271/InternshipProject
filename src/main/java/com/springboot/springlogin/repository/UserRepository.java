package com.springboot.springlogin.repository;

import com.springboot.springlogin.model.Role;
import com.springboot.springlogin.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // List<User> findAll();

    @Query(value = "SELECT * FROM users u WHERE u.username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String name);

    @Query(value = "SELECT * FROM users u WHERE u.id = ?1", nativeQuery = true)
    Optional<User> findById(Long id);

    @Query(value = "DELETE FROM users WHERE id = ?1", nativeQuery = true)
    User deleteUserById(Long id);

    @Query(value = "SELECT * FROM users u WHERE u.email = ?1", nativeQuery = true)
    List<User> findUserByEmail(String email);
}
