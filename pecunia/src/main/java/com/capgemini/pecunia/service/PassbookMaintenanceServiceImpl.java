package com.capgemini.pecunia.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.capgemini.pecunia.dao.PassbookMaintenanceDAO;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.util.LoggerMessage;

@Component
public class PassbookMaintenanceServiceImpl implements PassbookMaintenanceService {
	
	@Autowired
	PassbookMaintenanceDAO passbookDAO;
	
	
//	Logger logger = Logger.getRootLogger();


	/*******************************************************************************************************
	 * - Function Name : updatePassbook(String accountId) 
	 * - Input Parameters : String accountId
	 * - Return Type : List 
	 * - Throws : PecuniaException 
	 * - Author : Mansi Agarwal
	 * - Creation Date : 24/09/2019 
	 * - Description : Update transaction details in passbook
	 ********************************************************************************************************/
	
	
	public List<Transaction> updatePassbook(String accountId) throws PecuniaException, PassbookException
	{
		
		try {
			List<Transaction> transactionList = new ArrayList<Transaction>();
 			Account account = new Account();
			account.setAccountId(accountId);
      		boolean accountExist = passbookDAO.accountValidation(account);
			if (!accountExist) {
				//logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new PassbookException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

			transactionList = passbookDAO.updatePassbook(accountId);
			System.out.println("here:"+transactionList);
			boolean ans = false;
			if (transactionList.size() > 0) {
				ans = passbookDAO.updateLastUpdated(account);
				if (ans) {
					System.out.println(ans);
					//logger.info(LoggerMessage.UPDATE_PASSBOOK_SUCCESSFUL);
				}
			}
			return transactionList;
		} catch (Exception e) {
			//logger.error(ErrorConstants.UPDATE_PASSBOOK_ERROR);
			throw new PassbookException(e.getMessage());
			
		}
	}

	/*******************************************************************************************************
	 * - Function Name : accountSummary(String accountId, Date startDate, Date endDate) 
	 * - Input Parameters : String accountId, Date startDate, Date endDate
	 * - Return Type : List 
	 * - Throws : PecuniaException 
	 * - Author : Rishav Dev
	 * - Creation Date : 24/09/2019 
	 * - Description : Provides the account summary
	 ********************************************************************************************************/
	
	
	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate) throws PecuniaException, PassbookException {
		
		try {
			List<Transaction> transactionList = new ArrayList<Transaction>();
 			Account account = new Account();
			account.setAccountId(accountId);
			
      		boolean accountExist = passbookDAO.accountValidation(account);
			if (!accountExist) {
				//logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new PassbookException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

			transactionList = passbookDAO.accountSummary(accountId,startDate,endDate);
//			System.out.println("here:"+transactionList);
			
			return transactionList;
		} catch (Exception e) {
			//logger.error(ErrorConstants.UPDATE_PASSBOOK_ERROR);
			throw new PassbookException(e.getMessage());
			
		}
}
}