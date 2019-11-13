package com.capgemini.pecunia.service;

import static org.junit.Assert.assertNotNull;
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

import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;

@RunWith(SpringRunner.class)
@SpringBootTest
class PassbookMaintenanceServiceImplTest {

	
	@Autowired
	PassbookMaintenanceService pms;
	
	@AfterEach
	void tearDown() throws Exception {
		pms=null;
	}

	@Test
	@DisplayName("Passbook updated successfully")
	@Rollback(true)
	void testUpdatePassbook() throws PecuniaException, PassbookException {
		
		String accountId="100101000001";
		
		assertNotNull(pms.updatePassbook(accountId));
		
		
		
	}

	@Test
	@DisplayName("Passbook failed to update")
	@Rollback(true)
	void testUpdatePassbookFail() throws PecuniaException, PassbookException {
		
        String accountId="900909000009";
		
        assertThrows(PassbookException.class, () -> {
			pms.updatePassbook(accountId);
            });
		
	}
	
	

	@Test
	@DisplayName("Account summary successfully generated")
	@Rollback(true)
	void testAccountSummary() throws PecuniaException, PassbookException {
		
		String accountId="100101000002";
		String sDate1="2018-10-10";
		String sDate2="2019-11-12";
		LocalDate date1=LocalDate.parse(sDate1);
		LocalDate date2=LocalDate.parse(sDate2);
		
		assertNotNull(pms.accountSummary(accountId, date1, date2));
		
	}
	
	

}
