package com.capgemini.pecunia.dao;

import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;

@Component
public class TransactionDAOImpl implements TransactionDAO{

	@Override
	public double getBalance(Account account) throws PecuniaException, TransactionException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateBalance(Account account) throws PecuniaException, TransactionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int generateChequeId(Cheque cheque) throws PecuniaException, TransactionException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int generateTransactionId(Transaction transaction) throws PecuniaException, TransactionException {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
