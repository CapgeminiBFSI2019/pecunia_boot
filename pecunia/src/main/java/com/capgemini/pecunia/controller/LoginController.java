package com.capgemini.pecunia.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.model.Login;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;

import com.capgemini.pecunia.service.LoginService;
import com.google.gson.JsonObject;

@RestController
public class LoginController {
	
      
        @Autowired
		LoginService loginService;

		@CrossOrigin(origins = "http://localhost:4200")
		@PostMapping(path = "/login")
		
		
	
		public String validateEmail(@RequestBody Map<String, Object> requestData) {
			Login login = new Login(null);
			JsonObject dataResponse = new JsonObject();

			String username = requestData.get("username").toString();
			String password = requestData.get("password").toString();
			
			System.out.println(username + "\n" + password);
			login.setPassword(password);
			login.setUsername(username);

			try {
				boolean isValidated = loginService.validateEmail(login);
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("Login Id", isValidated);

			} catch (PecuniaException | LoginException e) {
				dataResponse.addProperty("success", false);
				dataResponse.addProperty("message", e.getMessage());
			}
			return dataResponse.toString();
		}
	}

//



