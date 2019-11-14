package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Login;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginServiceImplTest {


	@Autowired
	LoginService lgn;
	
	
	
	@AfterEach
	void tearDown() throws Exception {
		lgn=null;
	}

	@Test
	@DisplayName("Login Successful")
	@Rollback(true)
	void testValidateEmail() throws PecuniaException, LoginException {
		
		Login log = new Login();
		log.setUsername("anish@gmail.com");
		log.setPassword("12345");
		assertTrue(lgn.validateEmail(log));
	}
	
	
	@Test
	@DisplayName("Login Failed")
	@Rollback(true)
	void testValidateEmailFail() throws PecuniaException, LoginException {
		
		Login log = new Login();
		log.setUsername("agnibha@gmail.com");
		log.setPassword("67678gfg");
		assertThrows(PecuniaException.class, () -> {
			lgn.validateEmail(log);
            });
		
	}
	

}
