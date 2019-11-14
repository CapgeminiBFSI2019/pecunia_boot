package com.capgemini.pecunia.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.dao.AccountManagementDAO;
import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Address;
import com.capgemini.pecunia.model.Customer;
import com.capgemini.pecunia.util.Constants;

@Component
public class AccountManagementServiceImpl implements AccountManagementService {

	private static final Logger logger = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

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
			isUpdated = accountDAO.deleteAccount(account);

		} catch (Exception e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
		logger.info(Constants.DELETE_ACCOUNT_SUCCESSFUL);
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

			isUpdated = accountDAO.updateCustomerName(account, customer);
		} catch (Exception e) {
			logger.error(ErrorConstants.UPDATE_ACCOUNT_ERROR);
			throw new AccountException(e.getMessage());
		}
		logger.info(Constants.UPDATE_NAME_SUCCESSFUL);
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
			isUpdated = accountDAO.updateCustomerContact(account, customer);
		} catch (Exception e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
		logger.info(Constants.UPDATE_CONTACT_SUCCESSFUL);
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
			isUpdated = accountDAO.updateCustomerAddress(account, address);
		} catch (Exception e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(e.getMessage());
		}
		logger.info(Constants.UPDATE_ADDRESS_SUCCESSFUL);
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
			id = accountDAO.calculateAccountId(account);
			logger.info(Constants.ACCOUNT_ID_CALCULATED);
			return id;
		} catch (Exception e) {
			logger.error(ErrorConstants.TECH_ERROR);
			throw new AccountException(ErrorConstants.TECH_ERROR);
		}

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
			String accountId = calculateAccountId(account);
			account.setAccountId(accountId);
			account.setLastUpdated(LocalDateTime.now());
			String createdId = accountDAO.addAccount(account);
			if (createdId == null) {
				logger.error(ErrorConstants.ACCOUNT_CREATION_ERROR);
				throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
			}
			logger.info(Constants.ADD_ACCOUNT_SUCCESSFUL);
			return accountId;
		} catch (Exception e) {
			logger.error(ErrorConstants.ACCOUNT_CREATION_ERROR);
			throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
		}
	}

	/*******************************************************************************************************
	 * - Function Name : showAccountDetails(Account account) - Input Parameters :
	 * Account account - Return Type : boolean - Throws : AccountException - Author
	 * : Rohit Kumar - Creation Date : 24/09/2019 - Description : Showing Details of
	 * Account
	 * 
	 * @throws PecuniaException
	 ********************************************************************************************************/

	@Override
	public Account showAccountDetails(Account account) throws AccountException, PecuniaException {
		Account accountRequested = new Account();
		try {
			accountRequested = accountDAO.showAccountDetails(account);
		} catch (AccountException | PecuniaException e) {
			logger.error(ErrorConstants.NO_SUCH_ACCOUNT);
			throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return accountRequested;
	}

}
