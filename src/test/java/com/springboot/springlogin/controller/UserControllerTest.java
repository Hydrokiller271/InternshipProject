package com.springboot.springlogin.controller;


// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

// import com.springboot.configure.WebSecurityConfig;
import com.springboot.springlogin.model.User;
import com.springboot.springlogin.repository.UserRepository;
import com.springboot.springlogin.service.Impl.UserServiceImpl;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
public class UserControllerTest {
    @Autowired
	private MockMvc mockMvc;

	// @MockBean
	// private WebSecurityConfig webSC;

	@MockBean
	private UserServiceImpl userService;

	@MockBean
	private UserRepository userRepository;

    @Test
    void testAddNewUsers() throws Exception {
		User user = new User();
		user.setId((long) 1);
		user.setUsername("Hoang123");
		user.setPassword("password123");

		// Mockito.when(userService.).thenReturn(false);
		String url = "/users";

		this.mockMvc.perform(post(url)
						.param("username", user.getUsername())
						.param("password", user.getPassword()))
						.andExpect(status().isOk());
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
		User user = new User();
		user.setId((long) 1);
		user.setUsername("Hoang123");
		user.setPassword("password123");

		users.add(user);
		String url = "/users";

		Mockito.when(userService.getAllUsers()).thenReturn(users);
		this.mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].username", Matchers.equalTo("Hoang123")))
				.andExpect(jsonPath("$[0].password", Matchers.equalTo("password123")));
	
    }

	@Test
	public void testUpdateUser() throws Exception{
		long userId=1;
	
			
		
	}
}
