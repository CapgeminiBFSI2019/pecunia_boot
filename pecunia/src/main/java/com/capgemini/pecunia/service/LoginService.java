package com.capgemini.pecunia.service;


import org.springframework.stereotype.Service;

import com.capgemini.pecunia.model.Login;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;

@Service
public interface LoginService {

	public boolean validateEmail(Login log) throws PecuniaException,LoginException;


}
