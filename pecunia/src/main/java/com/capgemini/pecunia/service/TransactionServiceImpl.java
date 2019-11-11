package com.capgemini.pecunia.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.dao.TransactionDAO;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.util.Constants;

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
		int transId = 0;

		try {
			double beneficiaryBalance = 0, payeeBalance = 0, newBeneficiaryBalance = 0, newPayeeBalance = 0;

			String bankName = cheque.getBankName();

			Transaction creditTransaction, debitTransaction;

			Cheque chequeDetail;
			chequeDetail = new Cheque();
			chequeDetail.setNum(cheque.getNum());
			chequeDetail.setAccountNo(cheque.getAccountNo());
			chequeDetail.setBankName(cheque.getBankName());
			chequeDetail.setHolderName(cheque.getHolderName());
			chequeDetail.setIfsc(cheque.getIfsc());
			chequeDetail.setIssueDate(cheque.getIssueDate());

//			TransactionDAO transactionDAO = new TransactionDAOImpl();

			String accountId = transaction.getAccountId();
			Account requestedAccount = transactionDAO.getAccountById(accountId);

			if (!requestedAccount.getStatus().equals("Active")) {
//				logger.error(ErrorConstants.ACCOUNT_CLOSED);
				throw new TransactionException(ErrorConstants.ACCOUNT_CLOSED);
			} else {

				if ((bankName != Constants.BANK_NAME)
						&& (Arrays.asList(Constants.OTHER_BANK_NAME).contains(bankName))) {
					// other banks cheque
					chequeDetail.setStatus(Constants.CHEQUE_STATUS_PENDING);
					transId = transactionDAO.generateChequeId(chequeDetail);
				} else {
					if (!bankName.equals(Constants.BANK_NAME)) {
						// invalid bank cheque

//						logger.error(ErrorConstants.INVALID_BANK_EXCEPTION);
						throw new TransactionException(ErrorConstants.INVALID_BANK_EXCEPTION);
					} else {
						// pecunia cheque
						String payeeAccountId = transaction.getTransFrom();
						Account requestedPayeeAccount = transactionDAO.getAccountById(payeeAccountId);
						if (!requestedPayeeAccount.getStatus().equals("Active")) {
//							logger.error(ErrorConstants.ACCOUNT_CLOSED);
							throw new TransactionException(ErrorConstants.ACCOUNT_CLOSED);
						} else {

							if (transaction.getAmount() < Constants.MINIMUM_CHEQUE_AMOUNT
									|| transaction.getAmount() > Constants.MAXIMUM_CHEQUE_AMOUNT) {
								// invalid cheque amount

//								logger.error(ErrorConstants.INVALID_CHEQUE_EXCEPTION);
								throw new TransactionException(ErrorConstants.INVALID_CHEQUE_EXCEPTION);
							} else {

								Account beneficiaryAccount = new Account();
								beneficiaryAccount.setAccountId(transaction.getAccountId());

								Account payeeAccount = new Account();
								payeeAccount.setAccountId(transaction.getTransFrom());

								beneficiaryBalance = transactionDAO.getBalance(beneficiaryAccount);
								payeeBalance = transactionDAO.getBalance(payeeAccount);

								if (payeeBalance < transaction.getAmount()) {
									// cheque bounce
									chequeDetail.setStatus(Constants.CHEQUE_STATUS_BOUNCED);
									transId = transactionDAO.generateChequeId(chequeDetail);
								} else {
									chequeDetail.setStatus(Constants.CHEQUE_STATUS_CLEARED);
									int chequeId = transactionDAO.generateChequeId(chequeDetail);
									LocalDateTime transDate = LocalDateTime.now();
									newBeneficiaryBalance = beneficiaryBalance + transaction.getAmount();
									newPayeeBalance = payeeBalance - transaction.getAmount();

									beneficiaryAccount.setBalance(newBeneficiaryBalance);
									payeeAccount.setBalance(newPayeeBalance);

									creditTransaction = new Transaction();
									creditTransaction.setAccountId(transaction.getAccountId());
									creditTransaction.setType(Constants.TRANSACTION_CREDIT);
									creditTransaction.setAmount(transaction.getAmount());
									creditTransaction.setOption(Constants.TRANSACTION_OPTION_CHEQUE);
									creditTransaction.setChequeId(chequeId);
									creditTransaction.setTransFrom(transaction.getTransFrom());
									creditTransaction.setTransTo(Constants.NA);
									creditTransaction.setClosingBalance(newBeneficiaryBalance);
									creditTransaction.setTransDate(transDate);

									debitTransaction = new Transaction();
									debitTransaction.setAccountId(transaction.getTransFrom());
									debitTransaction.setType(Constants.TRANSACTION_DEBIT);
									debitTransaction.setAmount(transaction.getAmount());
									debitTransaction.setOption(Constants.TRANSACTION_OPTION_CHEQUE);
									debitTransaction.setChequeId(chequeId);
									debitTransaction.setTransFrom(Constants.NA);
									debitTransaction.setTransTo(transaction.getAccountId());
									debitTransaction.setClosingBalance(newPayeeBalance);
									debitTransaction.setTransDate(transDate);

									transId = transactionDAO.generateTransactionId(debitTransaction);
									transId = transactionDAO.generateTransactionId(creditTransaction);

									transactionDAO.updateBalance(beneficiaryAccount);
									transactionDAO.updateBalance(payeeAccount);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
//			logger.error(e.getMessage());
			throw new TransactionException(e.getMessage());
		}
//		logger.info(Constants.AMOUNT_DEBITED + transId);
		return transId;
	}

	@Override
	public int debitUsingCheque(Transaction transaction, Cheque cheque) throws TransactionException, PecuniaException {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
