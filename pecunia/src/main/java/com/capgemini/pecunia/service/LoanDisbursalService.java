package com.capgemini.pecunia.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;

@Service
public interface LoanDisbursalService {
	public ArrayList<Loan> retrieveAll() throws PecuniaException, IOException, LoanDisbursalException;

	public ArrayList<Loan> rejectedLoanRequests() throws PecuniaException, LoanDisbursalException, IOException;

	public ArrayList<Loan> approveLoan() throws IOException, PecuniaException, LoanDisbursalException;

	public ArrayList<LoanDisbursal> approvedLoanList() throws IOException, PecuniaException, LoanDisbursalException;

	public String updateLoanAccount(ArrayList<LoanDisbursal> updateLoanApprovals, int numberOfMonths)
			throws PecuniaException, LoanDisbursalException;

	public String updateLoanStatus(ArrayList<Loan> rejectedLoanList, ArrayList<Loan> approvedLoanList)
			throws PecuniaException, LoanDisbursalException;

	public ArrayList<String> updateExistingBalance(ArrayList<Loan> approvedLoanList,
			ArrayList<LoanDisbursal> approvedLoanLists)
			throws PecuniaException, TransactionException, LoanDisbursalException, IOException;

	public ArrayList<Loan> approveLoanWithoutStatus() throws IOException, PecuniaException, LoanDisbursalException;

}