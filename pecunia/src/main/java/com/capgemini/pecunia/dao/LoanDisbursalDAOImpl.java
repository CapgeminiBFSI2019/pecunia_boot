package com.capgemini.pecunia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Customer;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;
import com.capgemini.pecunia.repository.AccountRepository;
import com.capgemini.pecunia.repository.LoanDisbursalRepository;
import com.capgemini.pecunia.repository.LoanRequestRepository;

@Component
public class LoanDisbursalDAOImpl implements LoanDisbursalDAO {

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	LoanDisbursalRepository loanDisbursalRepository;
	@Autowired
	LoanRequestRepository loanRequestRepository;

	@Override
	public List<Loan> retrieveLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		System.out.println("here");
		List<Loan> reqList = loanRequestRepository.findAll();
		if (reqList.size() > 0) {
			return reqList;
		} else {
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
	}

	@Override
	public List<Loan> retrieveAcceptedLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		reqList = loanRequestRepository.findByScoreAndStatus();
		if (reqList.size() > 0) {
			return reqList;
		} else {
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}

	}

	@Override
	public List<Loan> retrieveRejectedLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		reqList = loanRequestRepository.findRejectedByScoreAndStatus();
		if (reqList.size() > 0) {
			return reqList;
		} else {
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
	}

	@Override
	public void releaseLoanSheet(ArrayList<Loan> loanList) throws IOException, PecuniaException {

		for (Loan loans : loanList) {
			LoanDisbursal loanDisbursalEntity = new LoanDisbursal();
			loanDisbursalEntity.setLoanId(loans.getLoanId());
			loanDisbursalEntity.setAccountId(loans.getAccountId());
			loanDisbursalEntity.setDisbursedAmount(loans.getAmount());
			loanDisbursalEntity.setDueAmount(amountToBePaid(loans.getEmi(), loans.getTenure()));
			loanDisbursalEntity.setNumberOfEmiToBePaid(loans.getTenure());
			loanDisbursalEntity.setLoanType(loans.getType());
			loanDisbursalEntity = loanDisbursalRepository.save(loanDisbursalEntity);

		}
	}

	@Override
	public List<LoanDisbursal> loanApprovedList() throws IOException, PecuniaException, LoanDisbursalException {
		List<LoanDisbursal> reqList = loanDisbursalRepository.findAll();
		System.out.println("idhar aaya");
		if (reqList.size() > 0) {
			return reqList;
		} else {
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
	}

	@Override
	public void updateLoanAccount( double dueAmount, double tenure, String accountId, int loanDisbursalId) throws IOException, PecuniaException, LoanDisbursalException {
		Optional<LoanDisbursal> loanDisbursal = loanDisbursalRepository.findById(loanDisbursalId);
		if(loanDisbursal.isPresent()) {
			LoanDisbursal loan = loanDisbursal.get();
			loan.setDueAmount(dueAmount);
			loan.setNumberOfEmiToBePaid(tenure);
			loanDisbursalRepository.save(loan);
			
		}
		else {
			throw new LoanDisbursalException(ErrorConstants.INVALID_ACCOUNT_EXCEPTION);
		}

	}

	@Override
	public void updateStatus(int loanID, String status) throws IOException, PecuniaException, LoanDisbursalException {

		Optional<Loan> loanRequest = loanRequestRepository.findById(loanID);
		if (loanRequest.isPresent()) {
			Loan loan = loanRequest.get();
			loan.setStatus(status);
			loanRequestRepository.save(loan);
		} else {
			throw new LoanDisbursalException(ErrorConstants.INVALID_LOAN_ACCOUNT);
		}

	}

	@Override
	public double totalEmi(String accountId) throws PecuniaException, LoanDisbursalException {
		double totalEMI = 0.0;
		totalEMI = loanRequestRepository.sumOfEmi(accountId);
		return totalEMI;

	}

	@Override
	public List<Loan> retrieveAcceptedLoanListWithoutStatus()
			throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<Loan> reqList = new ArrayList<>();
		reqList = (ArrayList<Loan>) loanRequestRepository.findByScore();
		if (reqList.size() > 0) {
			return reqList;
		} else {
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}

	}

	@Override
	public ArrayList<String> uniqueIds() throws IOException, PecuniaException, LoanDisbursalException {
		ArrayList<String> accountId = new ArrayList<>();
		try {

			accountId = loanDisbursalRepository.uniqueAccIds();
		} catch (Exception e) {

			throw new LoanDisbursalException(ErrorConstants.NO_UNIQUE_IDS);
		}
		return accountId;
	}

	private double amountToBePaid(double emi, int tenure) {
		return emi * tenure;
	}

}
