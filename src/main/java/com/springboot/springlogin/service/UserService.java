package com.springboot.springlogin.service;

import com.springboot.springlogin.model.Role;
import com.springboot.springlogin.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public List<Role> getAllRoles();

    public void addNewRole(Role role);

    public void deleteRole(String name);

    public void addNewUser(User user);

    public void deleteUser(Long id);

    public User findUserByEmail(String email);
}

