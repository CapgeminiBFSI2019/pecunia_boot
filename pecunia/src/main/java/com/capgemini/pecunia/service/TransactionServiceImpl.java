package com.capgemini.pecunia.service;

import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;

@Component
public class TransactionServiceImpl implements TransactionService{

	@Override
	public double getBalance(Account account) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateBalance(Account account) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return false;
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
