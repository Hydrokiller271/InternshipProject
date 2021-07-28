package com.springboot.springlogin.repository;

import com.springboot.springlogin.model.Role;
import com.springboot.springlogin.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM roles r WHERE r.name = ?1", nativeQuery = true)
    Optional<Role> findByName(String name);

    @Query(value = "DELETE FROM roles WHERE name = ?1", nativeQuery = true)
    Role deleteRollByName(String name);
}
