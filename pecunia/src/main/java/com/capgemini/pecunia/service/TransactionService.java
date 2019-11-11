package com.capgemini.pecunia.service;

import org.springframework.stereotype.Service;

import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;

@Service
public interface TransactionService {

	public double getBalance(Account account) throws TransactionException, PecuniaException;

	public boolean updateBalance(Account account) throws TransactionException, PecuniaException;

	public int creditUsingSlip(Transaction transaction) throws TransactionException, PecuniaException;

	public int debitUsingSlip(Transaction transaction) throws TransactionException, PecuniaException;

	public int creditUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException;

	public int debitUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException;
}
