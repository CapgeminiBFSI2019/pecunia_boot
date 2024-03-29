package com.capgemini.pecunia.service;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.dao.LoginDAO;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Login;
import com.capgemini.pecunia.repository.LoginRepository;
import com.capgemini.pecunia.util.Constants;
import com.capgemini.pecunia.util.Utility;

@Component
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginRepository loginRepository;
	@Autowired
	LoginDAO loginDAO;

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	/*******************************************************************************************************
	 * - Function Name : validateEmail(Login login) - Input Parameters : Login login
	 * Return Type : boolean - Throws : LoginException - Author : Kumar Saurabh -
	 * Creation Date : 24/09/2019 - Description : Validating an account by setting
	 * secretKey and checking validity by comparing password and hashPassword
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean validateEmail(Login login) throws PecuniaException, LoginException {
		boolean isValidated = false;

		String password = null;
		String secretKey = loginDAO.validateEmail(login);
		if (secretKey == null) {
			throw new LoginException(ErrorConstants.LOGIN_ERROR);
		} else {
			byte arr[] = null;
			try {
				arr = Utility.getSHA(login.getPassword() + secretKey);
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
				throw new LoginException(ErrorConstants.LOGIN_ERROR);
			}
			String hashPassword = Utility.toHexString(arr);
			try {
				password = loginDAO.fetchPassword(login);
				if (password.equals(hashPassword)) {
					isValidated = true;
					logger.info(Constants.LOGIN_SUCCESSFUL);
				} else {
					throw new LoginException(ErrorConstants.LOGIN_ERROR);
				}
			} catch (LoginException e) {
				logger.error(e.getMessage());
				throw new LoginException(ErrorConstants.LOGIN_ERROR);
			}
		}
		return isValidated;
	}

}
