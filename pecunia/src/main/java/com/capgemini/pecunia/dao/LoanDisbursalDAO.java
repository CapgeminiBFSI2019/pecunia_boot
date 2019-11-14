package com.capgemini.pecunia.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;

@Repository
public interface LoanDisbursalDAO {
	public List<Loan> retrieveLoanList() throws IOException, PecuniaException, LoanDisbursalException;

	public List<Loan> retrieveAcceptedLoanList() throws IOException, PecuniaException, LoanDisbursalException;

	public List<Loan> retrieveRejectedLoanList() throws IOException, PecuniaException, LoanDisbursalException;

	public void releaseLoanSheet(ArrayList<Loan> loanList) throws IOException, PecuniaException;

	public List<LoanDisbursal> loanApprovedList() throws IOException, PecuniaException, LoanDisbursalException;

	public void updateLoanAccount(double dueAmount, double tenure, String accountId, int loanDisbursalId)
			throws IOException, PecuniaException, LoanDisbursalException;

	public void updateStatus(int loanID, String Status) throws IOException, PecuniaException, LoanDisbursalException;

	public double totalEmi(String accountId) throws PecuniaException, LoanDisbursalException;

	public List<Loan> retrieveAcceptedLoanListWithoutStatus()
			throws IOException, PecuniaException, LoanDisbursalException;

	public ArrayList<String> uniqueIds() throws IOException, PecuniaException, LoanDisbursalException;

}
