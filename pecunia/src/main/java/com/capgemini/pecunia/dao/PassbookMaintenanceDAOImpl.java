package com.capgemini.pecunia.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.repository.AccountRepository;
import com.capgemini.pecunia.repository.PassbookRepository;


@Component
public class PassbookMaintenanceDAOImpl implements PassbookMaintenanceDAO{

	@Autowired
	PassbookRepository passbook;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
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
	public boolean updateLastUpdated(Account account) throws PecuniaException, PassbookException {
	
		Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
		boolean isUpdated = false;
		if(accountRequested.isPresent())
		{
			Account accountEntity = accountRequested.get();
			accountEntity.setLastUpdated(LocalDateTime.now().plusMinutes(330));
			accountRepository.save(accountEntity);
			isUpdated = true;
		}
		 System.out.println(isUpdated);
		return isUpdated;
	}

	@Override
	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate)
			throws PassbookException, PecuniaException {
		List<Transaction> transList = new ArrayList<>();
		
		try {

			transList= passbook.getAccountSummary(accountId, startDate, endDate);
         }
		catch(Exception e) {
            throw new PassbookException(ErrorConstants.TECH_ERROR);
        }
	 return transList;
	}

	@Override
	public boolean accountValidation(Account account) throws PecuniaException, PassbookException{
		Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
		if(accountRequested.isPresent())
		{
			return true;
		}
		 return false;
	}
	
}
