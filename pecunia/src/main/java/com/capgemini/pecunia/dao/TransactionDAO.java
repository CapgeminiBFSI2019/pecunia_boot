package com.capgemini.pecunia.dao;

import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;

@Repository
public interface TransactionDAO {
	public Account getAccountById(String id) throws PecuniaException, TransactionException;

	public double getBalance(Account account) throws PecuniaException, TransactionException;

	public boolean updateBalance(Account account) throws PecuniaException, TransactionException;

	public int generateChequeId(Cheque cheque) throws PecuniaException, TransactionException;

	public int generateTransactionId(Transaction transaction) throws PecuniaException, TransactionException;
}
