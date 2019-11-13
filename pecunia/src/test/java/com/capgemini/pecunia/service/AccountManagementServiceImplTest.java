package com.capgemini.pecunia.service;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Ignore;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Address;
import com.capgemini.pecunia.model.Customer;
import com.capgemini.pecunia.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountManagementServiceImplTest {

	@Autowired
	AccountManagementService ams;
	
	@AfterEach
	void tearDown() {
		ams=null;
	}
	
	@Test
	@DisplayName("Account successfully deleted")
	@Rollback(true)
	void testDeleteAccount() throws PecuniaException, AccountException {
		Account account = new Account();
		account.setAccountId("100101000007");
		assertTrue(ams.deleteAccount(account));
	}
	
	
	@Test
	@DisplayName("Account failed to delete")
	@Rollback(true)
	void testDeleteAccountFail() throws PecuniaException, AccountException {
		Account account = new Account();
		account.setAccountId("900909000009");
		assertThrows(AccountException.class, () -> {
			ams.deleteAccount(account);
            });
	}
	
	

	@Test
	@DisplayName("Customer name successfully updated")
	@Rollback(true)
	void testUpdateCustomerName() throws PecuniaException, AccountException {
		Account account = new Account();
		Customer customer = new Customer();
		account.setAccountId("100101000001");
		customer.setName("Vidushi");
		
		assertTrue(ams.updateCustomerName(account, customer));
		
	}
	
	
	@Test
	@DisplayName("Customer name failed to update. Account already closed")
	@Rollback(true)
	void testUpdateCustomerNameClosed() throws PecuniaException, AccountException {
		Account account = new Account();
		Customer customer = new Customer();
		account.setAccountId("100101000003");
		customer.setName("Vidushi");
		
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerName(account, customer);
            });
		
		
	}
	

	@Test
	@DisplayName("Customer name failed to update")
	@Rollback(true)
	void testUpdateCustomerNameFail() throws PecuniaException, AccountException {
		Account account = new Account();
		Customer customer = new Customer();
		account.setAccountId("800909000009");
		customer.setName("Vidushi");
		
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerName(account, customer);
            });
        
	}
	
	@Test
	@DisplayName("Customer contact successfully updated")
	@Rollback(true)
	void testUpdateCustomerContact() throws PecuniaException, AccountException {
		Account account = new Account();
		Customer customer = new Customer();
		account.setAccountId("100101000001");
		customer.setContact("9852001301");
		
		assertTrue(ams.updateCustomerContact(account, customer));
	}

	
	@Test
	@DisplayName("Customer Contact failed to update")
	@Rollback(true)
	void testUpdateCustomerContactFail() throws PecuniaException, AccountException {
		Account account = new Account();
		Customer customer = new Customer();
		account.setAccountId("800909000009");
		customer.setContact("9852001301");
		
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerContact(account, customer);
            });
		
	}
	
	
	
	@Test
	@DisplayName("Customer address successfully updated")
	@Rollback(true)
	void testUpdateCustomerAddress() throws PecuniaException, AccountException {
		Account account = new Account();
		Address address = new Address();
		account.setAccountId("100101000001");
		address.setAddressLine1("jshbijws");
		address.setAddressLine2("sgeds");
		address.setCity("bangalore");
		address.setState("Karnataka");
		address.setCountry("India");
		address.setZipcode("500076");
		
		assertTrue(ams.updateCustomerAddress(account, address));
		
	}

	
	@Test
	@DisplayName("Customer address failed to update")
	@Rollback(true)
	void testUpdateCustomerAddressFail() throws PecuniaException, AccountException {
		Account account = new Account();
		Address address = new Address();
		account.setAccountId("800909000009");
		address.setAddressLine1("jshbijws");
		address.setAddressLine2("sgeds");
		address.setCity("bangalore");
		address.setState("Karnataka");
		address.setCountry("India");
		address.setZipcode("500076");
		
		assertThrows(AccountException.class, () -> {
			ams.updateCustomerAddress(account, address);
            });
	
	}


	
	
	
	@Test
	@Ignore
	@DisplayName("Account successfully created")
	@Rollback(true)
	void testAddAccount() throws PecuniaException, AccountException {
		
		Account account = new Account();
		Customer customer = new Customer();
		Address address = new Address();
		address.setAddressLine1("jshbijws");
		address.setAddressLine2("sgeds");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setZipcode("400076");

		customer.setName("Avizek");
		customer.setAadhar("741333333541");
		customer.setPan("PMNLT8706Q");
		customer.setContact("8876320888");
		customer.setGender("F");
		LocalDate dob = LocalDate.parse("1995-10-16");
		customer.setDob(dob);

		account.setType("FD");
		account.setBalance(9000.00);
		account.setBranchId("1002");
		account.setInterest(6.76);
		account.setStatus(Constants.ACCOUNT_STATUS[0]);

		assertNotNull(ams.addAccount(customer, address, account));
		
		
	}


	@Test
	@DisplayName("Account failed to create")
	@Rollback(true)
	void testAddAccountFail() throws PecuniaException, AccountException {
		
		Account account = new Account();
		Customer customer = new Customer();
		Address address = new Address();
		address.setAddressLine1("jshbijws");
		address.setAddressLine2("sgeds");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setZipcode("400076");

		customer.setName("Avizek");
		customer.setAadhar("9852000012367");
		customer.setPan("PMNBL8705KD");
		customer.setContact("876334904");
		customer.setGender("M");
		LocalDate dob = LocalDate.parse("1995-10-16");
		customer.setDob(dob);

		account.setType("FD");
		account.setBalance(9000.00);
		account.setBranchId("1002");
		account.setInterest(6.76);
		account.setStatus(Constants.ACCOUNT_STATUS[0]);

		assertThrows(AccountException.class, () -> {
			ams.addAccount(customer, address, account);
            });
		
		
	}


}
