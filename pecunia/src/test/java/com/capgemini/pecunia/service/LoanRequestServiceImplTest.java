package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.model.LoanRequest;
import com.capgemini.pecunia.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoanRequestServiceImplTest {

	
	@Autowired
	LoanRequestService lrs;
	
	
	@AfterEach
	void tearDown() throws Exception {
		
	lrs=null;
	
	}

	@Test
	@DisplayName("Loan request successfully created")
	@Rollback(true)
	void testCreateLoanRequest() throws LoanException {
		
		LoanRequest loan = new LoanRequest();
		loan.setAccountId("100101000001");
		loan.setAmount(50000.00);
		loan.setCreditScore(650);
		loan.setRoi(8.7);
		loan.setStatus(Constants.LOAN_REQUEST_STATUS[0]);
		loan.setTenure(25);
		loan.setType(Constants.LOAN_TYPE[1]);
		
		assertNotNull(lrs.createLoanRequest(loan));
		
	}

}
