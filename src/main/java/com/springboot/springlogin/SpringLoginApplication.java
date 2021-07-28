package com.springboot.springlogin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javafaker.Faker;
import com.springboot.springlogin.model.AuthenticationResponse;
import com.springboot.springlogin.model.CustomUserDetails;
import com.springboot.springlogin.model.User;
import com.springboot.springlogin.service.JwtUserDetailsService;
import com.springboot.springlogin.service.Impl.UserServiceImpl;
import com.springboot.springlogin.util.JwtRequestFiller;
import com.springboot.springlogin.util.JwtUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
public class SpringLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLoginApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Profile("!test")
	@Bean
	CommandLineRunner commandLineRunner(
			UserServiceImpl userService,
			BCryptPasswordEncoder bCryptPasswordEncoder
			) {
		return args -> {
			Faker faker = new Faker();
			String email ="";
			for(int i = 0;i<20;i++) {
				String username = faker.name().username();
				String color = faker.color().name();
				//System.out.println(color);
				User user = new User(username,bCryptPasswordEncoder.encode(color),username+"@gmail.com");
				email = username;
				userService.save(user);
			}
			User editUser = userService.findUserByEmail(email+"@gmail.com");
			editUser.setUsername("this person now have a new name");
			userService.save(editUser);
			/*
			Always convert a DTO before setting a relationship
			Example : convert RoleDto into Role
			convert UserDto into User
			Role.setUser(User)
			Never give DTO a relationship however DTO can still display relationship
			if original object had one
			 */

		};
	}

	@RestController
class HelloWorldController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
		String encodedPassword = authenticationRequest.getPassword();
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), encodedPassword)
			);
			
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}



}
