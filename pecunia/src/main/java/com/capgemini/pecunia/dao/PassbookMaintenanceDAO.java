package com.capgemini.pecunia.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Transaction;

@Repository
public interface PassbookMaintenanceDAO {
	public List<Transaction> updatePassbook(String accountId) throws PassbookException, PecuniaException;

	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate)
			throws PassbookException, PecuniaException;

	public boolean accountValidation(Account account) throws PecuniaException, PassbookException;

	boolean updateLastUpdated(Account account) throws PecuniaException, PassbookException;
}
