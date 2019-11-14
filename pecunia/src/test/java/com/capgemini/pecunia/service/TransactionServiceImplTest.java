package com.capgemini.pecunia.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionServiceImplTest {

	
	@Autowired
	TransactionService tms;
	
	@AfterEach
	void tearDown() throws Exception {
	
		tms=null;
		
	}
	
	
	@Test
	@DisplayName("Fetching balance details successful")
	@Rollback(true)
	void testGetBalance() throws TransactionException, PecuniaException {
		
		Account account = new Account();
		account.setAccountId("100101000001");
		assertNotNull(tms.getBalance(account));
			
	}

	
	@Test
	@DisplayName("Update balance successful")
	@Rollback(true)
	void testUpdateBalance() throws TransactionException, PecuniaException {
		
		Account account = new Account();
		account.setAccountId("100101000001");
		account.setBalance(10000);
		assertTrue(tms.updateBalance(account));
		
		
	}

	
	@Test
	@DisplayName("Update balance failed")
	@Rollback(true)
	void testUpdateBalanceFail() throws TransactionException, PecuniaException {
		
		Account account = new Account();
		account.setAccountId("900909000009");
		account.setBalance(10000);
			
		assertThrows(TransactionException.class, () -> {
			tms.updateBalance(account);
            });
	
		
		
	}
	
	@Test
	@DisplayName("Credit using slip successful")
	@Rollback(true)
	void testCreditUsingSlip() throws TransactionException, PecuniaException {
		
		Transaction transaction = new Transaction();
		transaction.setAccountId("100402000002");
		transaction.setAmount(5000);
		assertNotNull(tms.creditUsingSlip(transaction));
		
		
		
		
	}
	
	
	@Test
	@DisplayName("Credit using slip failed")
	@Rollback(true)
	void testCreditUsingSlipFail() throws TransactionException, PecuniaException {
		
		Transaction transaction = new Transaction();
		transaction.setAccountId("900909000009");
		transaction.setAmount(5000);
		
		assertThrows(TransactionException.class, () -> {
			tms.creditUsingSlip(transaction);
            });
		
	}
	

	
	@Test
	@DisplayName("Debit using slip successful")
	@Rollback(true)
	void testDebitUsingSlip() throws TransactionException, PecuniaException {
		

		Transaction transaction = new Transaction();
		transaction.setAccountId("100402000002");
		transaction.setAmount(1200);
		assertNotNull(tms.debitUsingSlip(transaction));


	}
	
	
	@Test
	@DisplayName("Debit using slip failed")
	@Rollback(true)
	void testDebitUsingSlipfail() throws TransactionException, PecuniaException {
		

		Transaction transaction = new Transaction();
		transaction.setAccountId("900909000009");
		transaction.setAmount(1200);
		
		assertThrows(TransactionException.class, () -> {
			tms.debitUsingSlip(transaction);
            });


	}

	
	@Test
	@DisplayName("Credit using cheque successful")
	@Rollback(true)
	void testCreditUsingCheque() throws TransactionException, PecuniaException {
		
		LocalDate issueDate=LocalDate.parse("2019-09-20");
		
		Transaction creditChequeTransaction = new Transaction();
		creditChequeTransaction.setAmount(5500.00);
		creditChequeTransaction.setAccountId("100101000001");
		creditChequeTransaction.setTransTo("100101000001");
		creditChequeTransaction.setTransFrom("100101000002");

		Cheque creditCheque = new Cheque();
		creditCheque.setAccountNo("100101000002");
		creditCheque.setHolderName("Avizek");
		creditCheque.setIfsc("PBIN0000001");
		creditCheque.setIssueDate(issueDate);
		creditCheque.setNum(Integer.parseInt("122222"));
		creditCheque.setBankName(Constants.BANK_NAME);
     
		
      assertNotNull(tms.creditUsingCheque(creditChequeTransaction, creditCheque));
      
  

		
	}
	
	
	@Test
	@DisplayName("Credit using cheque failed")
	@Rollback(true)
	void testCreditUsingChequeFail() throws TransactionException, PecuniaException {
		
		LocalDate issueDate=LocalDate.parse("2019-09-20");
		
		Transaction creditChequeTransaction = new Transaction();
		creditChequeTransaction.setAmount(5500.00);
		creditChequeTransaction.setAccountId("900909000009");
		creditChequeTransaction.setTransTo("900909000009");
		creditChequeTransaction.setTransFrom("900909000009");

		Cheque creditCheque = new Cheque();
		creditCheque.setAccountNo("900909000009");
		creditCheque.setHolderName("Avizek");
		creditCheque.setIfsc("PBIN0000001");
		creditCheque.setIssueDate(issueDate);
		creditCheque.setNum(Integer.parseInt("122222"));
		creditCheque.setBankName(Constants.BANK_NAME);
     
		
     
      
  	assertThrows(TransactionException.class, () -> {
		tms.creditUsingCheque(creditChequeTransaction, creditCheque);
        });
		
	}
	

	
	@Test
	@DisplayName("Debit using cheque successful")
	@Rollback(true)
	void testDebitUsingCheque() throws TransactionException, PecuniaException {
		
		LocalDate issueDate=LocalDate.parse("2019-09-20");
		Transaction debitChequeTransaction = new Transaction();
		debitChequeTransaction.setAmount(500);
		debitChequeTransaction.setAccountId("100101000001");

		Cheque debitCheque = new Cheque();
		debitCheque.setAccountNo("100101000001");
		debitCheque.setHolderName("avizek");
		debitCheque.setIfsc("PBIN0000001");
		debitCheque.setIssueDate(issueDate);
		debitCheque.setNum(134562);
		
		assertNotNull(tms.debitUsingCheque(debitChequeTransaction, debitCheque));
		
	}
	
	
	@Test
	@DisplayName("Debit using cheque failed")
	@Rollback(true)
	void testDebitUsingChequeFail() throws TransactionException, PecuniaException {
		
		LocalDate issueDate=LocalDate.parse("2019-09-20");
		Transaction debitChequeTransaction = new Transaction();
		debitChequeTransaction.setAmount(500);
		debitChequeTransaction.setAccountId("900909000009");

		Cheque debitCheque = new Cheque();
		debitCheque.setAccountNo("900909000009");
		debitCheque.setHolderName("avizek");
		debitCheque.setIfsc("PBIN0000001");
		debitCheque.setIssueDate(issueDate);
		debitCheque.setNum(134562);
		
		
		assertThrows(TransactionException.class, () -> {
			tms.debitUsingCheque(debitChequeTransaction, debitCheque);
	        });
		
	}
	
	

}
