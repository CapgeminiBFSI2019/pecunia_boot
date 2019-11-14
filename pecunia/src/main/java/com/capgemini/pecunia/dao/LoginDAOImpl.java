package com.capgemini.pecunia.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Login;
import com.capgemini.pecunia.repository.LoginRepository;
//import com.capgemini.pecunia.util.HibernateUtil;

@Component
public class LoginDAOImpl implements LoginDAO {
	@Autowired
	LoginRepository loginRepository;

	private static final Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);

	@Override
	public String validateEmail(Login login) throws PecuniaException, LoginException {
		String secretKey = null;
		Optional<Login> loginRequested = loginRepository.findById(login.getUsername());

		if (loginRequested.isPresent()) {
			secretKey = loginRequested.get().getSecretKey();
		} else {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new PecuniaException(ErrorConstants.NO_SUCH_ACCOUNT);

		}
		return secretKey;
	}

	@Override
	public String fetchPassword(Login login) throws PecuniaException, LoginException {

		String password = null;
		Optional<Login> loginRequested = loginRepository.findById(login.getUsername());

		if (loginRequested.isPresent()) {
			password = loginRequested.get().getPassword();
		} else {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new PecuniaException(ErrorConstants.LOGIN_ERROR);
		}
		return password;

	}

}
