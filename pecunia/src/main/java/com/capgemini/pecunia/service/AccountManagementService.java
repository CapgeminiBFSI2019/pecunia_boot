package com.capgemini.pecunia.service;


import org.springframework.stereotype.Service;

import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Address;
import com.capgemini.pecunia.model.Customer;

@Service
public interface AccountManagementService {
	public boolean deleteAccount(Account account) throws PecuniaException, AccountException;

	public boolean updateCustomerName(Account account, Customer customer) throws PecuniaException, AccountException;

	public boolean updateCustomerContact(Account account, Customer customer) throws PecuniaException, AccountException;

	public boolean updateCustomerAddress(Account account, Address address) throws PecuniaException, AccountException;

	public String addAccount(Customer customer, Address address, Account account) throws PecuniaException, AccountException;

	public String calculateAccountId(Account account) throws PecuniaException, AccountException;
	
	public Account showAccountDetails(Account account) throws AccountException,PecuniaException;
}
