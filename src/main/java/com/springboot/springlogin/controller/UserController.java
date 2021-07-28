package com.springboot.springlogin.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.springboot.springlogin.service.UserService;
import com.springboot.springlogin.model.Role;
import com.springboot.springlogin.model.User;
import com.springboot.springlogin.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/email")
  public User getUserByEmail(@RequestBody User user){
    return userService.findUserByEmail(user.getEmail());
  }

  @PostMapping("/users")
  public ResponseEntity<Object> addNewUsers(@RequestBody User user) {
    try {
      userService.addNewUser(user);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    } catch (Exception e) {
      return ResponseEntity.status(409).build();
    }
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.status(200).build();
  }
}