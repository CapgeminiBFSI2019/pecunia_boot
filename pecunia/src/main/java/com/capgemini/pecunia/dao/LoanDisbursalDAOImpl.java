package com.capgemini.pecunia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
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
		ArrayList<Loan> reqList = new ArrayList<>();
		reqList = (ArrayList<Loan>) loanRequestRepository.findAll();
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
	public void updateLoanAccount(ArrayList<LoanDisbursal> loanApprovals, double dueAmount, double tenure,
			String accountId, int loanDisbursalId) throws IOException, PecuniaException, LoanDisbursalException {
		loanDisbursalRepository.updateLoanDisbursal(dueAmount, tenure, accountId);

	}

	@Override
	public void updateStatus(ArrayList<Loan> loanRequests, int loanID, String status)
			throws IOException, PecuniaException, LoanDisbursalException {

		loanRequestRepository.updateStatusOfRequest(status, loanID);

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
