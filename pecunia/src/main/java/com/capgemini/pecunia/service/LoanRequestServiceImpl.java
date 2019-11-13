package com.capgemini.pecunia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.dao.LoanRequestDAO;
import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.LoanRequest;
import com.capgemini.pecunia.repository.AccountRepository;

@Component
public class LoanRequestServiceImpl implements LoanRequestService{

	@Autowired
	AccountRepository accountRepository;
	@Autowired
	LoanRequestDAO loanDAO;
		//Logger logger = Logger.getRootLogger();

		/*******************************************************************************************************
		 * -Function Name : calculateEMI(double amount, int tenure, double roi) -Input
		 * Parameters : double amount, int tenure, double roi -Return Type : double
		 * -Author : Rishabh Rai -Creation Date : 24/09/2019 -Description : Takes the
		 * Amount,tenure and Rate of Interest as parameter and returns emi for the loan
		 ********************************************************************************************************/

		public static double calculateEMI(double amount, int tenure, double roi) {
			double p = amount;
			double r = roi / 1200;
			double a = Math.pow(1 + r, tenure);
			double emi = (p * r * a) / (a - 1);
			return Math.round(emi);
		}

		/*******************************************************************************************************
	 * -Function Name :createLoanRequest(Loan loan) -Input Parameters : Loan loan
		 * -Return Type : boolean -Author : Rishabh Rai -Creation Date : 24/09/2019
		 * -@Throws LoanException -Description : Create entry for loan Request
	 ********************************************************************************************************/

		public int createLoanRequest(LoanRequest loan) throws LoanException {
			int loanId = 0;
		try { 
			Optional<Account> accountRequested = accountRepository.findById(loan.getAccountId());
			if(accountRequested.isPresent())
			{
				loan.setEmi(LoanRequestServiceImpl.calculateEMI(loan.getAmount(), loan.getTenure(), loan.getRoi()));
				loanId = loanDAO.addLoanDetails(loan);
			}
			else
			{
				throw new PecuniaException(ErrorConstants.NO_SUCH_ACCOUNT) ;
				
			}
			} catch (Exception e) {
				//logger.error(e.getMessage());
				throw new LoanException(e.getMessage());

			}
			return loanId;

		}
	}

