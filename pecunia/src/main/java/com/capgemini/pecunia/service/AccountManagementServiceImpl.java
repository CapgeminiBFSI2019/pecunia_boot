package com.capgemini.pecunia.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.pecunia.dao.AccountManagementDAO;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Address;
import com.capgemini.pecunia.model.Customer;
import com.capgemini.pecunia.util.Constants;

@Service
public class AccountManagementServiceImpl implements AccountManagementService {

//	Logger logger = Logger.getRootLogger();

	public AccountManagementServiceImpl() {
	}

	@Autowired
	AccountManagementDAO accountDAO;
	@Autowired
	AccountManagementService accManagementService;
	

	/*******************************************************************************************************
	 * - Function Name : deleteAccount(Account account) - Input Parameters : Account
	 * account - Return Type : boolean - Throws : AccountException - Author : Rohit
	 * Kumar - Creation Date : 24/09/2019 - Description : Deleting an account by
	 * setting account status "Closed"
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean deleteAccount(Account account) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		try {

			boolean isValidated = validateAccountId(account);
			if (isValidated) {

				

				isUpdated = accountDAO.deleteAccount(account);
			} else {
//				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
//		logger.info(Constants.DELETE_ACCOUNT_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerName(Account account, Customer customer) -
	 * Input Parameters : Account account, Customer customer - Return Type : boolean
	 * - Throws : AccountException - Author : Aditi Singh - Creation Date :
	 * 24/09/2019 - Description : Updating customer name
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		try {

//			boolean isValidated = validateAccountId(account);
//			System.out.println(isValidated + "in service");
//			if (isValidated) {
//			

				isUpdated = accountDAO.updateCustomerName(account, customer);
				System.out.println("isUpdated: "+isUpdated);
//			} else {
////				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
//				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
//			}

		} catch (Exception e) {
//			logger.error(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			throw new AccountException(e.getMessage());
		}
//		logger.info(Constants.UPDATE_NAME_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerContact(Account account, Customer customer) -
	 * Input Parameters : Account account, Customer customer - Return Type : boolean
	 * - Throws : AccountException - Author : Aditi Singh - Creation Date :
	 * 24/09/2019 - Description : Updating customer contact
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		try {

//			boolean isValidated = validateAccountId(account);
//			if (isValidated) {
				isUpdated = accountDAO.updateCustomerContact(account, customer);
//			} else {
////				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
//				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
//			}
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
//		logger.info(Constants.UPDATE_CONTACT_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : updateCustomerName(Account account, Address address) -
	 * Input Parameters : Account account, Address address - Return Type : boolean -
	 * Throws : AccountException - Author : Aditi Singh - Creation Date : 24/09/2019
	 * - Description : Updating customer address
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException {

		boolean isUpdated = false;
		try {
//			boolean isValidated = validateAccountId(account);
//			if (isValidated) {
				isUpdated = accountDAO.updateCustomerAddress(account, address);
//			} else {
////				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
//				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
//			}
		} catch (Exception e) {
//			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
//		logger.info(Constants.UPDATE_ADDRESS_SUCCESSFUL);
		return isUpdated;
	}

	/*******************************************************************************************************
	 * - Function Name : calculateAccountId(Account account) - Input Parameters :
	 * Account account - Return Type : String - Throws : AccountException - Author :
	 * Aditi Singh - Creation Date : 24/09/2019 - Description : Generation of a new
	 * account ID with the given branch ID and type of Account
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public String calculateAccountId(Account account) throws PecuniaException, AccountException {
		try {
			String id = "";
			id = id.concat(account.getBranchId());
			String type = account.getType();
			switch (type) {
			case Constants.SAVINGS:
				id = id.concat(Constants.CODE_SAVINGS);
				break;
			case Constants.CURRENT:
				id = id.concat(Constants.CODE_CURRENT);
				break;
			case Constants.FD:
				id = id.concat(Constants.CODE_FD);
				break;
			case Constants.LOAN:
				id = id.concat(Constants.CODE_LOAN);
				break;
			}
			account.setAccountId(id);
			System.out.println("id going into query: "+id);
			id = accountDAO.calculateAccountId(account);
//			logger.info(Constants.ACCOUNT_ID_CALCULATED);
			return id;
		} catch (Exception e) {
//			logger.error(ErrorConstants.TECH_ERROR);
			throw new AccountException(ErrorConstants.TECH_ERROR);
		}

	}

	/*******************************************************************************************************
	 * - Function Name : validateAccountId(Account account) - Input Parameters :
	 * Account account - Return Type : double - Throws : AccountException - Author :
	 * Aditi Singh - Creation Date : 24/09/2019 - Description : Validation of
	 * Account ID
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public boolean validateAccountId(Account account) throws PecuniaException, AccountException {
		boolean isValidated = false;
		boolean doesExist = false;

		try {
			doesExist = accountDAO.validateAccountId(account);
			if(doesExist) {
				isValidated=true;
			}
		}catch (Exception e) {
//			logger.error(ErrorConstants.ACCOUNT_CLOSED);
			throw new AccountException(e.getMessage());
		}
//		logger.info(Constants.ACCOUNT_ID_VALIDATED);
		return isValidated;
	}

	/*******************************************************************************************************
	 * - Function Name : addAccount(Customer customer, Address address,Account
	 * account) - Input Parameters : Customer customer, Address address,Account
	 * account - Return Type : String - Throws : AccountException - Author : Vidushi
	 * Razdan - Creation Date : 24/09/2019 - Description : Addition of new Account
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	public String addAccount(Customer customer, Address address, Account account)
			throws PecuniaException, AccountException {
		try {
			String custId = accountDAO.addCustomerDetails(customer, address);
			account.setCustomerId(custId);
			System.out.println("cust Id: "+custId);
			String accountId = calculateAccountId(account);
			System.out.println("accountId: "+ accountId);
			account.setAccountId(accountId);
			account.setLastUpdated(LocalDateTime.now());
			String createdId = accountDAO.addAccount(account);
			if (createdId == null) {
//				logger.error(ErrorConstants.ACCOUNT_CREATION_ERROR);
				throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
			}
//			logger.info(Constants.ADD_ACCOUNT_SUCCESSFUL);
			return accountId;
		} catch (Exception e) {
//			logger.error(ErrorConstants.ACCOUNT_CREATION_ERROR);

			throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
		}
	}
	
	/*******************************************************************************************************
	 * - Function Name : showAccountDetails(Account account) - Input Parameters : Account
	 * account - Return Type : boolean - Throws : AccountException - Author : Rohit
	 * Kumar - Creation Date : 24/09/2019 - Description : Showing Details of Account
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	@Override
	public Account showAccountDetails(Account account) throws AccountException, PecuniaException {
		Account accountRequested = new Account();
		try {
			boolean isValidated = validateAccountId(account);
			if (isValidated) {
				accountRequested = accountDAO.showAccountDetails(account);
			} else {
//				logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}

			
		} catch (AccountException | PecuniaException e) {
//			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return accountRequested;
	}

}
