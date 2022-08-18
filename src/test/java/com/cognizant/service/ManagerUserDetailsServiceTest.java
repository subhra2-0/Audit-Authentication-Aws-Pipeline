package com.cognizant.service;

import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.model.ProjectManager;
import com.cognizant.repository.ManagerRepository;




@SpringBootTest
public class ManagerUserDetailsServiceTest {

UserDetails userdetails;
	
	@InjectMocks
	ManagerDetailsService managerdetailservice;

	@Mock
	ManagerRepository userservice;
	
	@Test
	public void loadUserByUsernameTest() {
		
		ProjectManager user1=new ProjectManager("admin","admin",null);
		Optional<ProjectManager> data =Optional.of(user1) ;
		when(userservice.findById("admin")).thenReturn(data);
		UserDetails loadUserByUsername2 = managerdetailservice.loadUserByUsername("admin");
		assertEquals(user1.getUserId(),loadUserByUsername2.getUsername());
	}
//	@Test
//	public void saveUser() {
//		ProjectManager user1=new ProjectManager("admin2","admin2",null);
//		managerdetailservice.saveUser(user1);
//		
//		assertNotEquals("admin",managerdetailservice.loadUserByUsername("admin").getUsername());
//		
//	}

}
