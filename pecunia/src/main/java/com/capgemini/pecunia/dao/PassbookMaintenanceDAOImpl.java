package com.capgemini.pecunia.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.repository.PassbookRepository;


@Component
public class PassbookMaintenanceDAOImpl implements PassbookMaintenanceDAO{

	@Autowired
	PassbookRepository passbook;
	
	public List<Transaction> updatePassbook(String accountId) throws PassbookException, PecuniaException {
		List<Transaction> transList = new ArrayList<>();
		
		try {

			transList= passbook.findById(accountId, LocalDateTime.now().plusMinutes(330));
         }
		catch(Exception e) {
            throw new PassbookException(ErrorConstants.UPDATE_PASSBOOK_ERROR);
        }
	 return transList;
	}

	@Override
	public boolean updateLastUpdated(String accountId) throws PecuniaException, PassbookException {
	
		boolean isUpdated = false;
		try {

			isUpdated= passbook.findByTime( LocalDateTime.now().plusMinutes(330), accountId);
         
		}
		catch(Exception e) {
            throw new PassbookException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
        }
		return isUpdated;
	}

	@Override
	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate)
			throws PassbookException, PecuniaException {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
