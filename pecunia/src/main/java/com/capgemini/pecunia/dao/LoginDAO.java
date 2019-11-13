package com.capgemini.pecunia.dao;
import org.springframework.stereotype.Repository;
//import com.capgemini.pecunia.dto.Login;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Login;

@Repository
public interface LoginDAO {
	public String validateEmail(Login login) throws PecuniaException, LoginException;

	public String fetchPassword(Login login) throws PecuniaException, LoginException;
}
