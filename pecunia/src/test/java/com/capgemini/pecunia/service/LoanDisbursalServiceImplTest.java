package com.capgemini.pecunia.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.model.LoanDisbursal;
@RunWith(SpringRunner.class)
@SpringBootTest
class LoanDisbursalServiceImplTest {

	@Autowired
	LoanDisbursalService lds;
	
	
	
	@AfterEach
	void tearDown() throws Exception {
		lds=null;
	}

	@Test
	@DisplayName("Test case failed. Data is not present in loan disbursed database")
	@Rollback(true)
	void testUpdateLoanAccount() {
		
		ArrayList<LoanDisbursal>updateLoanApprovals  = null;
		assertThrows(LoanDisbursalException.class, () -> {
			lds.updateLoanAccount(updateLoanApprovals, 25);
            });
		
		
	}

	@Test
	@DisplayName("Test case failed. No loan status to update")
	@Rollback(true)
	void testUpdateLoanStatus() {
		
		
		assertThrows(LoanDisbursalException.class, () -> {
			lds.updateLoanStatus(null, null);
            });
		
	}

	@Test
	@DisplayName("Test case failed. No existing balance")
	@Rollback(true)
	void testUpdateExistingBalance() {
		
		assertThrows(LoanDisbursalException.class, () -> {
			lds.updateExistingBalance(null, null);
            });
		
		
		
	}

}
