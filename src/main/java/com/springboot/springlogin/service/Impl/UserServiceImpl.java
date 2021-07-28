package com.springboot.springlogin.service.Impl;

import com.springboot.springlogin.repository.RoleRepository;
import com.springboot.springlogin.repository.UserRepository;
import com.springboot.springlogin.dto.UserDto;
import com.springboot.springlogin.model.Role;
import com.springboot.springlogin.model.User;
import com.springboot.springlogin.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  // @Autowired
  //   private ConvertServiceImpl convertService;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }
  
  @Override
  public void addNewUser(User user) {
    Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
    if (userOptional.isPresent()) {
      throw new IllegalStateException("Username has been taken");
    }
    userRepository.save(user);
  }

  @Override
  public void addNewRole(Role role) {
    Optional<Role> roleOptional = roleRepository.findByName(role.getName());
    if (roleOptional.isPresent()) {
      throw new IllegalStateException("This role has already been taken");
    }
    roleRepository.save(role);
  }

  public void saveNewRole(Role role) {
    roleRepository.save(role);
  }

  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    if (!userOptional.isPresent()) {
      throw new IllegalStateException("No known user.");
    }
    User deletedUser = userOptional.get();
    deletedUser.setRoles(null);
    userRepository.deleteUserById(id);
  }

  @Override
  public void deleteRole(String name) {
    Optional<Role> roleOptional = roleRepository.findByName(name);
    if (!roleOptional.isPresent()) {
      throw new IllegalStateException("No known role.");
    }
    roleRepository.deleteRollByName(name);
  }

  @Override
	public User findUserByEmail(String email) {
    List<User> users = userRepository.findUserByEmail(email);
    return users.get(0);
  }
  // public List<UserDto> findByEmail(String email){
  //   List<User> users = userRepository.findUserByEmail(email);
  //   return convertService.convertUserListToDto(users);
  // }

	public boolean checkUserByEmail(String email){
		boolean status = false;
		// User checkUser = userRepository.findUserByEmail(email);
		// if(checkUser != null){
		// 	status = true;
		// }
		return status;
	}
  

}
