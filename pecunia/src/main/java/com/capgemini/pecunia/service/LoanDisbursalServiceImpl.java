package com.capgemini.pecunia.service;

import java.io.IOException;
import java.util.ArrayList;

import com.capgemini.pecunia.dao.LoanDisbursalDAOImpl;
import com.capgemini.pecunia.dao.TransactionDAOImpl;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;
import com.capgemini.pecunia.util.Constants;


public class LoanDisbursalServiceImpl {
	TransactionDAOImpl transactionDAOImpl = new TransactionDAOImpl();
	ArrayList<LoanDisbursal> approvedLoanList = new ArrayList<LoanDisbursal>();

	/*******************************************************************************************************
	 * - Function Name : retrieveAll() - Input Parameters : None - Return Type :
	 * ArrayList<Loan> - Throws : PecuniaException, IOException,
	 * LoanDisbursalException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Retrieves loan requests
	 ********************************************************************************************************/

	public ArrayList<Loan> retrieveAll() throws PecuniaException, IOException, LoanDisbursalException {

		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
		ArrayList<Loan> retrievedLoanRequests = new ArrayList<Loan>();
		retrievedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveLoanList();
		if (retrievedLoanRequests.size() == 0) {
			
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);

		}
	
		return retrievedLoanRequests;
	}

	/*******************************************************************************************************
	 * - Function Name : approveLoan(ArrayList<Loan> loanRequestList) - Input
	 * Parameters : ArrayList<Loan> loanRequestList - Return Type : void - Throws :
	 * IOException, PecuniaException, LoanDisbursalException - Author : aninrana -
	 * Creation Date : 25/09/2019 - Description : Aprroving the loan request based
	 * on condition
	 ********************************************************************************************************/

	public ArrayList<Loan> approveLoan() throws IOException, PecuniaException, LoanDisbursalException {
		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
		ArrayList<Loan> acceptedLoanRequests = new ArrayList<Loan>();
		acceptedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveAcceptedLoanList();
		if (acceptedLoanRequests.size() == 0) {
		
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);

		}
		for (int index = 0; index < acceptedLoanRequests.size(); index++) {
			int loanId = acceptedLoanRequests.get(index).getLoanId();
			loanDisbursedDAO.updateStatus(acceptedLoanRequests, loanId, Constants.LOAN_REQUEST_STATUS[1]);
		}
		loanDisbursedDAO.releaseLoanSheet(acceptedLoanRequests);
	
		return acceptedLoanRequests;

	}

	/*******************************************************************************************************
	 * - Function Name : approveLoan(ArrayList<Loan> loanRequestList) - Input
	 * Parameters : ArrayList<Loan> loanRequestList - Return Type : void - Throws :
	 * IOException, PecuniaException, LoanDisbursalException - Author : aninrana -
	 * Creation Date : 25/09/2019 - Description : Aprroving the loan request based
	 * on condition
	 ********************************************************************************************************/

	public ArrayList<Loan> approveLoanWithoutStatus() throws IOException, PecuniaException, LoanDisbursalException {
		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
		ArrayList<Loan> acceptedLoanRequests = new ArrayList<Loan>();
		acceptedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveAcceptedLoanListWithoutStatus();
		if (acceptedLoanRequests.size() == 0) {
		
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);

		}

		return acceptedLoanRequests;

	}

	/*******************************************************************************************************
	 * - Function Name : approvedLoanList() - Input Parameters : None - Return Type
	 * : ArrayList<LoanDisbursal> - Throws : PecuniaException,IOException - Author :
	 * aninrana - Creation Date : 25/09/2019 - Description : retrieving the loan
	 * disbursed data
	 * 
	 * @throws LoanDisbursalException
	 ********************************************************************************************************/

	public ArrayList<LoanDisbursal> approvedLoanList() throws IOException, PecuniaException, LoanDisbursalException {
		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();

		approvedLoanList = (ArrayList<LoanDisbursal>) loanDisbursedDAO.loanApprovedList();
		if (approvedLoanList.size() == 0) {
			
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}

		return approvedLoanList;

	}

	/*******************************************************************************************************
	 * - Function Name : rejectedLoanRequests() - Input Parameters : void - Return
	 * Type : ArrayList<Loan> - Throws : PecuniaException, LoanDisbursalException -
	 * Author : aninrana - Creation Date : 25/09/2019 - Description : Retrieving the
	 * rejected loan rejected
	 * 
	 * @throws IOException
	 ********************************************************************************************************/

	public ArrayList<Loan> rejectedLoanRequests() throws PecuniaException, LoanDisbursalException, IOException {

		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
		ArrayList<Loan> rejectedLoanRequests = new ArrayList<Loan>();
		rejectedLoanRequests = (ArrayList<Loan>) loanDisbursedDAO.retrieveRejectedLoanList();
		if (rejectedLoanRequests.size() == 0) {
			
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
		for (int index = 0; index < rejectedLoanRequests.size(); index++) {
			int loanId = rejectedLoanRequests.get(index).getLoanId();
			loanDisbursedDAO.updateStatus(rejectedLoanRequests, loanId, Constants.LOAN_REQUEST_STATUS[2]);
		}
		
		return rejectedLoanRequests;
	}

	/*******************************************************************************************************
	 * - Function Name : updateLoanAccount(ArrayList<LoanDisbursal>
	 * updateLoanApprovals, int numberOfMonths) - Input Parameters :
	 * ArrayList<LoanDisbursal> updateLoanApprovals, int numberOfMonths - Return
	 * Type : void - Throws : throws PecuniaException - Author : aninrana - Creation
	 * Date : 25/09/2019 - Description : Updating the data in loan disbursed
	 * database
	 * 
	 * @throws LoanDisbursalException
	 ********************************************************************************************************/

	public String updateLoanAccount(ArrayList<LoanDisbursal> updateLoanApprovals, int numberOfMonths)
			throws PecuniaException, LoanDisbursalException {
		String status = Constants.STATUS_CHECK[0];
		if (updateLoanApprovals != null) {
			LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
			for (int index = 0; index < updateLoanApprovals.size(); index++) {
				double updatedDueAmount = updateLoanApprovals.get(index).getDisbursedAmount()
						- (updateLoanApprovals.get(index).getDisbursedAmount()
								/ updateLoanApprovals.get(index).getNumberOfEmiToBePaid()) * numberOfMonths;

		

				double updatedTenure = updateLoanApprovals.get(index).getNumberOfEmiToBePaid() - numberOfMonths;


				String accountId = updateLoanApprovals.get(index).getAccountId();
				int loanDisbursalId = updateLoanApprovals.get(index).getLoanDisbursalId();


				try {
				
					loanDisbursedDAO.updateLoanAccount(updateLoanApprovals, updatedDueAmount, updatedTenure, accountId,
							loanDisbursalId);
			
				} catch (Exception e) {
			
					throw new LoanDisbursalException(e.getMessage());
				}

			}
		}

		else {
			status = Constants.STATUS_CHECK[1];
		
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}

		return status;
	}

	/*******************************************************************************************************
	 * - Function Name : updateLoanStatus(ArrayList<Loan> rejectedLoanList) - Input
	 * Parameters : ArrayList<Loan> rejectedLoanList - Return Type : void - Throws :
	 * PecuniaException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating the loan status after operation
	 * 
	 * @throws LoanDisbursalException
	 ********************************************************************************************************/

	public String updateLoanStatus(ArrayList<Loan> rejectedLoanList, ArrayList<Loan> approvedLoanList)
			throws PecuniaException, LoanDisbursalException {
		String status = Constants.STATUS_CHECK[0];
		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
		if (rejectedLoanList != null || approvedLoanList != null) {
			try {
				for (int index = 0; index < rejectedLoanList.size(); index++) {
					int loanId = rejectedLoanList.get(index).getLoanId();
					loanDisbursedDAO.updateStatus(rejectedLoanList, loanId, Constants.LOAN_REQUEST_STATUS[2]);

				}

				for (int index = 0; index < approvedLoanList.size(); index++) {
					int loanId = approvedLoanList.get(index).getLoanId();
					loanDisbursedDAO.updateStatus(approvedLoanList, loanId, Constants.LOAN_REQUEST_STATUS[1]);

				}

			} catch (Exception e) {
			
				throw new LoanDisbursalException(e.getMessage());
			}

		} else {
			status = Constants.STATUS_CHECK[1];
		
			throw new LoanDisbursalException(ErrorConstants.NO_LOAN_REQUESTS);
		}
	
		return status;
	}

	/*******************************************************************************************************
	 * - Function Name : updateExistingBalance(ArrayList<Loan> approvedLoanRequests)
	 * - Input Parameters : ArrayList<Loan> approvedLoanRequests - Return Type :
	 * void - Throws : PecuniaException, TransactionException,
	 * LoanDisbursalException - Author : aninrana - Creation Date : 25/09/2019 -
	 * Description : Updating the Account balance of the customer
	 * 
	 * @throws IOException
	 ********************************************************************************************************/

	public ArrayList<String> updateExistingBalance(ArrayList<Loan> approvedLoanRequests,
			ArrayList<LoanDisbursal> approvedLoanList)
			throws PecuniaException, TransactionException, LoanDisbursalException, IOException {
		LoanDisbursalDAOImpl loanDisbursedDAO = new LoanDisbursalDAOImpl();
		ArrayList<String> status = new ArrayList<String>();
		ArrayList<String> accId = new ArrayList<String>();
		accId = loanDisbursedDAO.uniqueIds();
		for (int i = 0; i < accId.size(); i++) {
			Account account = new Account();
			account.setAccountId(accId.get(i));
			double oldBalance = transactionDAOImpl.getBalance(account);
			double totalEMI = loanDisbursedDAO.totalEmi(accId.get(i));
			System.out.println(totalEMI);
			double updatedBalance = oldBalance - totalEMI;

			if (updatedBalance < 0) {
				status.add("Not enough balance for account number " + accId.get(i) + " Balance left " + oldBalance);
			} else {
				account.setBalance(updatedBalance);
				transactionDAOImpl.updateBalance(account);
				updateLoanAccount(approvedLoanList, 1);
				status.add("Balance updated for " + accId.get(i) + " Amount deducted " + totalEMI + " Balance left "
						+ updatedBalance);
			}

		}

		return status;
	}

}
