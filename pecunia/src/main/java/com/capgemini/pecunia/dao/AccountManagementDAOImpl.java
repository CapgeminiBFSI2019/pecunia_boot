package com.capgemini.pecunia.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Address;
import com.capgemini.pecunia.model.Customer;
import com.capgemini.pecunia.repository.AccountRepository;
import com.capgemini.pecunia.repository.AddressRepository;
import com.capgemini.pecunia.repository.CustomerRepository;
import com.capgemini.pecunia.util.Constants;

@Component
public class AccountManagementDAOImpl implements AccountManagementDAO {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AddressRepository addressRepository;

	private static final Logger logger = LoggerFactory.getLogger(AccountManagementDAOImpl.class);

	@Override
	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId = null;
		try {
			Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
			if (accountRequested.isPresent()) {
				if (Constants.ACCOUNT_STATUS[1].equals(accountRequested.get().getStatus())) {
					throw new AccountException(ErrorConstants.CLOSED_ACCOUNT);
				}
				custId = accountRequested.get().getCustomerId();
				Optional<Customer> customerRequested = customerRepository.findById(custId);
				if (customerRequested.isPresent()) {
					Customer newCustomer = customerRequested.get();
					newCustomer.setName(customer.getName());
					newCustomer = customerRepository.save(newCustomer);
					isUpdated = true;
				}
			} else {
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(Account account) throws PecuniaException, AccountException {
		boolean isDeleted = false;
		try {
			Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
			if (accountRequested.isPresent()) {
				if (Constants.ACCOUNT_STATUS[1].equals(accountRequested.get().getStatus())) {
					throw new AccountException(ErrorConstants.CLOSED_ACCOUNT);
				}
				Account newAccount = accountRequested.get();
				newAccount.setStatus(Constants.ACCOUNT_STATUS[1]);
				newAccount = accountRepository.save(newAccount);
				isDeleted = true;
			} else {
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(e.getMessage());
		}

		return isDeleted;
	}

	@Override
	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId = null;
		try {
			Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
			if (accountRequested.isPresent()) {
				if (Constants.ACCOUNT_STATUS[1].equals(accountRequested.get().getStatus())) {
					throw new AccountException(ErrorConstants.CLOSED_ACCOUNT);
				}
				custId = accountRequested.get().getCustomerId();
				Optional<Customer> customerRequested = customerRepository.findById(custId);
				if (customerRequested.isPresent()) {
					Customer newCustomer = customerRequested.get();
					newCustomer.setContact(customer.getContact());
					newCustomer = customerRepository.save(newCustomer);
					isUpdated = true;
				}
			} else {
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	@Override
	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException {
		boolean isUpdated = false;
		String custId = null;
		String addrId = null;
		try {
			Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
			if (accountRequested.isPresent()) {
				if (Constants.ACCOUNT_STATUS[1].equals(accountRequested.get().getStatus())) {
					throw new AccountException(ErrorConstants.CLOSED_ACCOUNT);
				}
				custId = accountRequested.get().getCustomerId();
				Optional<Customer> customerRequested = customerRepository.findById(custId);
				if (customerRequested.isPresent()) {
					addrId = customerRequested.get().getAddressId();
					Optional<Address> addressRequested = addressRepository.findById(addrId);
					if (addressRequested.isPresent()) {
						Address newAddress = addressRequested.get();
						newAddress.setAddressLine1(address.getAddressLine1());
						newAddress.setAddressLine2(address.getAddressLine2());
						newAddress.setCity(address.getCity());
						newAddress.setState(address.getState());
						newAddress.setCountry(address.getCountry());
						newAddress.setZipcode(address.getZipcode());
						newAddress = addressRepository.save(newAddress);
						isUpdated = true;
					}
				}
			} else {
				throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(e.getMessage());
		}
		return isUpdated;
	}

	@Override
	public String addCustomerDetails(Customer customer, Address address)
			throws PecuniaException, AccountException, SQLException {

		String addrId = null;
		String custId = null;
		try {
			Address newAddress = new Address();
			Customer newCustomer = new Customer();
			newAddress.setAddressLine1(address.getAddressLine1());
			newAddress.setAddressLine2(address.getAddressLine2());
			newAddress.setCity(address.getCity());
			newAddress.setState(address.getState());
			newAddress.setCountry(address.getCountry());
			newAddress.setZipcode(address.getZipcode());
			newAddress = addressRepository.save(newAddress);
			addrId = newAddress.getId();
			newCustomer.setName(customer.getName());
			newCustomer.setGender(customer.getGender());
			newCustomer.setDob(customer.getDob());
			newCustomer.setContact(customer.getContact());
			newCustomer.setAadhar(customer.getAadhar());
			newCustomer.setPan(customer.getPan());
			newCustomer.setAddressId(addrId);
			newCustomer = customerRepository.save(newCustomer);
			custId = newCustomer.getCustomerId();
		} catch (Exception e) {
			throw new AccountException(ErrorConstants.ADD_DETAILS_ERROR);
		}
		return custId;
	}

	@Override
	public String addAccount(Account account) throws PecuniaException, AccountException, SQLException {
		try {
			Account newAccount = new Account();
			newAccount.setAccountId(account.getAccountId());
			newAccount.setCustomerId(account.getCustomerId());
			newAccount.setBalance(account.getBalance());
			newAccount.setBranchId(account.getBranchId());
			newAccount.setInterest(account.getInterest());
			newAccount.setType(account.getType());
			newAccount.setStatus(Constants.ACCOUNT_STATUS[0]);
			newAccount.setLastUpdated(account.getLastUpdated());
			newAccount = accountRepository.save(newAccount);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
		}
		return account.getAccountId();	
	}

	@Override
	public String calculateAccountId(Account account) throws PecuniaException, AccountException {
		long oldId = 0;
		String oldIdstr = null;
		String id = null;
		try {
			List<Account> accList = accountRepository
					.findByAccountIdLikeOrderByAccountIdDesc(account.getAccountId() + "%");
			if (accList.size() != 0) {
				Account accountRequested = accList.get(0);
				oldIdstr = accountRequested.getAccountId();
			} else {
				oldIdstr = account.getAccountId() + "00000";
			}
			oldId = Long.parseLong(oldIdstr);
			id = Long.toString(oldId + 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AccountException(ErrorConstants.ACCOUNT_CREATION_ERROR);
		}
		return id;
	}

	@Override
	public Account showAccountDetails(Account account) throws AccountException, PecuniaException {
		Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
		Account accountNew;
		if (accountRequested.isPresent()) {
			accountNew = accountRequested.get();
		} else {
			throw new AccountException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return accountNew;
	}
}
