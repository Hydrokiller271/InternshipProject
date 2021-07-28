package com.springboot.springlogin.controller;

import java.net.URI;
import java.util.List;

import com.springboot.springlogin.service.UserService;
import com.springboot.springlogin.model.Role;
import com.springboot.springlogin.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class RoleController {

  @Autowired
  private UserService userService;

  @GetMapping("/roles")
  public List<Role> getAllRoles() {
    return userService.getAllRoles();
  }

  @PostMapping("/roles")
  public ResponseEntity<Object> addNewRole(@RequestBody Role role) {
    try {
      userService.addNewRole(role);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(role.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    } catch (Exception e) {
      return ResponseEntity.status(409).build();
    }
  }

  @DeleteMapping("/roles/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return ResponseEntity.status(200).build();
  }

}