package com.capgemini.pecunia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.dao.TransactionDAO;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;

@Component
public class TransactionServiceImpl implements TransactionService{
//	Logger logger = Logger.getRootLogger();
	
	@Autowired
	TransactionDAO transactionDAO;
	
	@Override
	public double getBalance(Account account) throws TransactionException, PecuniaException {
		try {
			double balance;
			balance = transactionDAO.getBalance(account);
			return balance;
		} catch (PecuniaException e) {
//			logger.error(e.getMessage());
			throw new TransactionException(ErrorConstants.NO_SUCH_ACCOUNT);
		} catch (Exception e) {
//			logger.error(e.getMessage());
			throw new TransactionException(ErrorConstants.FETCH_ERROR);
		}
	}

	@Override
	public boolean updateBalance(Account account) throws TransactionException, PecuniaException {
		try {
			boolean success = false;
			success = transactionDAO.updateBalance(account);
			return success;
		} catch (Exception e) {

//			logger.error(e.getMessage());
			throw new TransactionException(ErrorConstants.UPDATE_ACCOUNT_ERROR);
		}
	}

	@Override
	public int creditUsingSlip(Transaction transaction) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int debitUsingSlip(Transaction transaction) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int creditUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int debitUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
