package com.capgemini.pecunia.dao;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Login;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoginException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.repository.LoginRepository;
//import com.capgemini.pecunia.util.HibernateUtil;

@Component
public class LoginDAOImpl implements LoginDAO {
	@Autowired
	LoginRepository loginRepository;
//	Logger logger = Logger.getRootLogger();

	@Override
	public String validateEmail(Login login) throws PecuniaException, LoginException {
		String secretKey = null;
		Optional<Login> loginRequested = loginRepository.findById(login.getUsername());

//		org.hibernate.Transaction transaction = null;
		
////			Session session = HibernateUtil.getSessionFactory().openSession();
//			transaction = session.beginTransaction();
//			@SuppressWarnings("rawtypes")
//			Query query = session.createNamedQuery("LoginEntity.getsecret_keyByusername");
//			query.setParameter("email", login.getUsername());
//			query.setMaxResults(1);
//			LoginEntity loginEntity = (LoginEntity) query.uniqueResult();
			if(loginRequested.isPresent()) {				
				secretKey = loginRequested.get().getSecretKey();
			} else {
//				logger.info(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new PecuniaException(ErrorConstants.NO_SUCH_ACCOUNT);
				
			}

	
		
		
	
//	catch (Exception e) {
//		logger.error(e.getMessage());
//		throw new PecuniaException(e.getMessage());
	
		return secretKey;

	}

	@Override
	public String fetchPassword(Login login) throws PecuniaException, LoginException {

		String password = null;
		Optional<Login> loginRequested = loginRepository.findById(login.getUsername());

//		org.hibernate.Transaction transaction = null;
//		try {
//			Session session = HibernateUtil.getSessionFactory().openSession();
//			transaction = session.beginTransaction();
//			@SuppressWarnings("rawtypes")
//			Query query = session.createNamedQuery("LoginEntity.getpasswordByusername");
//			query.setParameter("email", login.getUsername());
//			query.setMaxResults(1);
//			LoginEntity loginEntity = (LoginEntity) query.uniqueResult();
//			if (loginEntity != null) {
			if(loginRequested.isPresent()) {
				password = loginRequested.get().getPassword();
			} else {
//				logger.info(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new PecuniaException(ErrorConstants.LOGIN_ERROR);
			}

//			transaction.commit();
//			session.close();
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			throw new PecuniaException(e.getMessage());
//		}
		return password;

	}

}
