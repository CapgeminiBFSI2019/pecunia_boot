package com.capgemini.pecunia.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

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
		
	
	}

	@Test
	@DisplayName("")
	@Rollback(true)
	void testUpdateCustomerName() {
		
	}

	@Test
	@DisplayName("")
	@Rollback(true)
	void testUpdateCustomerContact() {
		
	}

	@Test
	@DisplayName("")
	@Rollback(true)
	void testUpdateCustomerAddress() {
		
	}

	@Test
	@DisplayName("")
	@Rollback(true)
	void testCalculateAccountId() {
		
	}

	@Test
	@DisplayName("")
	@Rollback(true)
	void testValidateAccountId() {
		
	}

	@Test
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
		customer.setAadhar("159162356569");
		customer.setPan("GHJKL8765E");
		customer.setContact("6832555559");
		customer.setGender("M");
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
	@DisplayName("")
	@Rollback(true)
	void testShowAccountDetails() {
		
	}

}
