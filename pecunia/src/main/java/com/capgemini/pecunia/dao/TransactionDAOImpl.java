package com.capgemini.pecunia.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.repository.AccountRepository;
import com.capgemini.pecunia.repository.ChequeRepository;
import com.capgemini.pecunia.repository.TransactionRepository;

@Component
public class TransactionDAOImpl implements TransactionDAO{
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	ChequeRepository chequeRepository;
	@Autowired
	TransactionRepository transactionRepository;
	
	@Override
	public double getBalance(Account account) throws PecuniaException, TransactionException {
		System.out.println("getbalance mai :"+account.getAccountId());
		Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
		double balance = 0.0;
		if(accountRequested.isPresent())
		{
			balance = accountRequested.get().getBalance();
		}
		else
		{
			throw new TransactionException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return balance;
	}

	@Override
	public boolean updateBalance(Account account) throws PecuniaException, TransactionException {
		Optional<Account> accountRequested = accountRepository.findById(account.getAccountId());
		boolean success = false;
		if(accountRequested.isPresent())
		{
			Account accountEntity = accountRequested.get();
			accountEntity.setBalance(account.getBalance());
			accountRepository.save(accountEntity);
			success = true;
		}
		else
		{
			throw new TransactionException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return success;
	}

	@Override
	public int generateChequeId(Cheque cheque) throws PecuniaException, TransactionException {
		int chequeId = 0;
		try {
			Cheque chequeEntity = new Cheque();
			chequeEntity.setNum(cheque.getNum());
			chequeEntity.setAccountNo(cheque.getAccountNo());
			chequeEntity.setHolderName(cheque.getHolderName());
			chequeEntity.setBankName(cheque.getBankName());
			chequeEntity.setIfsc(cheque.getIfsc());
			chequeEntity.setIssueDate(cheque.getIssueDate());
			chequeEntity.setStatus(cheque.getStatus());
			chequeEntity = chequeRepository.save(chequeEntity);
			chequeId = chequeEntity.getId();
		}
		catch(Exception e) {
//			logger.error(ErrorConstants.CHEQUE_INSERTION_ERROR);
			throw new PecuniaException(ErrorConstants.CHEQUE_INSERTION_ERROR);
		}
		return chequeId;
	}

	@Override
	public int generateTransactionId(Transaction transaction) throws PecuniaException, TransactionException {
		int transactionId = 0;
		try {
			Transaction transactionEntity = new Transaction();
			transactionEntity.setAccountId(transaction.getAccountId());
			transactionEntity.setType(transaction.getType());
			transactionEntity.setAmount(transaction.getAmount());
			transactionEntity.setOption(transaction.getOption());
			transactionEntity.setTransDate(transaction.getTransDate().plusMinutes(330));
			transactionEntity.setChequeId(transaction.getChequeId());
			transactionEntity.setTransFrom(transaction.getTransFrom());
			transactionEntity.setTransTo(transaction.getTransTo());
			transactionEntity.setClosingBalance(transaction.getClosingBalance());
			transactionEntity = transactionRepository.save(transactionEntity);
			transactionId = transactionEntity.getId();
		} catch (Exception e) {
//			logger.error(ErrorConstants.TRANSACTION_INSERTION_ERROR);
			throw new PecuniaException(ErrorConstants.TRANSACTION_INSERTION_ERROR);
		}
		return transactionId;
	}

	@Override
	public Account getAccountById(String id) throws PecuniaException, TransactionException {
		Optional<Account> account = accountRepository.findById(id);
		Account requestedAccount = null;
		if(account.isPresent())
		{
			requestedAccount = account.get();
		}
		else
		{
			throw new TransactionException(ErrorConstants.NO_SUCH_ACCOUNT);
		}
		return requestedAccount;
	}
	
}
