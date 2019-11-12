package com.capgemini.pecunia.service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Transaction;

public interface PassbookMaintenanceService {
	

	public List<Transaction> updatePassbook(String accountId) throws PecuniaException, PassbookException;
	public List<Transaction> accountSummary(String accountId, LocalDate startDate, LocalDate endDate) throws PecuniaException, PassbookException;

}
