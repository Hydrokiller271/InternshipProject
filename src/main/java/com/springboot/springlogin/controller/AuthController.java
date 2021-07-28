package com.springboot.springlogin.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.springlogin.dto.UserDto;
import com.springboot.springlogin.model.AuthenticationResponse;
import com.springboot.springlogin.model.User;
import com.springboot.springlogin.service.JwtUserDetailsService;
import com.springboot.springlogin.service.Impl.UserServiceImpl;
import com.springboot.springlogin.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserServiceImpl userService;

    private ResponseEntity<?> createAuthenticationToken(UserDto userDto){
        String encodedPassword = userDto.getPassword();
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), encodedPassword));
        } catch(BadCredentialsException e){
            return new ResponseEntity<>("Incorrect username or password",
                    HttpStatus.FORBIDDEN);   
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> payload){
        String email = null;
        String password = null;
        // try {
            email = payload.get("email").toString();
            password = payload.get("password").toString();
        // }catch(Exception e){
        //     return new ResponseEntity<>(new Exception("Missing value field !")
        //             , HttpStatus.FORBIDDEN);
        // }
        UserDto userDto = new UserDto(email, password);
        return createAuthenticationToken(userDto);
    }

    @RequestMapping(value = "/signup",
                    method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) throws Exception{
        // String email = null;
        // String username = null;
        // String password = null;
        // try{
        //     email = payload.get("email").toString();
        //     username = payload.get("username").toString();
        //     password = payload.get("password").toString();
        //     password = bCryptPasswordEncoder.encode(password);
        // }catch(Exception e){
        //     return new ResponseEntity<>(new Exception("Missing value field !"), HttpStatus.FORBIDDEN);
        // }
        // if(userService.checkUserByEmail(email)){
        //     return new ResponseEntity<>(new Exception("There is already an account with the email address: "+ email),
        //     HttpStatus.FORBIDDEN);
        // }
        // User user = new User(username, password, email);
        // try{
        //     userService.save(user);
        // }catch(Exception e){
        //     return new ResponseEntity<>(new Exception("Could not signup"),
        //             HttpStatus.FORBIDDEN);
        // }
        
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("status", true);
        objectNode.put("message", "Account creation successful");

        return new ResponseEntity<>(objectNode, HttpStatus.OK);
    }
}
