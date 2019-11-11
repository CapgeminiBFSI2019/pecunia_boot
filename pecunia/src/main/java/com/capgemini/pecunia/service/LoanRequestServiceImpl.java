package com.capgemini.pecunia.service;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.model.LoanRequest;

public class LoanRequestServiceImpl implements LoanRequestService{

	@Override
	public int createLoanRequest(LoanRequest loan) throws LoanException {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public int createLoanRequest(Loan loan) throws LoanException {
//		// TODO Auto-generated method stub
//		@Autowired
//		com.capgemini.pecunia.hibernate.dao.LoanDAO loanDao;
//
//		@Autowired
//		AccountManagementService accountManagementService;
//
//		Logger logger = Logger.getRootLogger();
//
//		/*******************************************************************************************************
//		 * -Function Name : calculateEMI(double amount, int tenure, double roi) -Input
//		 * Parameters : double amount, int tenure, double roi -Return Type : double
//		 * -Author : Rishabh Rai -Creation Date : 24/09/2019 -Description : Takes the
//		 * Amount,tenure and Rate of Interest as parameter and returns emi for the loan
//		 ********************************************************************************************************/
//
//		public static double calculateEMI(double amount, int tenure, double roi) {
//			double p = amount;
//			double r = roi / 1200;
//			double a = Math.pow(1 + r, tenure);
//			double emi = (p * r * a) / (a - 1);
//			return Math.round(emi);
//		}
//
//		/*******************************************************************************************************
//		 * -Function Name :createLoanRequest(Loan loan) -Input Parameters : Loan loan
//		 * -Return Type : boolean -Author : Rishabh Rai -Creation Date : 24/09/2019
//		 * -@Throws LoanException -Description : Create entry for loan Request
//		 ********************************************************************************************************/
//
//		public int createLoanRequest(Loan loan) throws LoanException {
//
//			boolean isValidAccount = false;
//			int loanId = 0;
//			try {
//				Account account = new Account();
//				account.setId(loan.getAccountId());
//				isValidAccount = accountManagementService.validateAccountId(account);
//				if (isValidAccount) {
//					loan.setEmi(LoanServiceImpl.calculateEMI(loan.getAmount(), loan.getTenure(), loan.getRoi()));
//					loanId = loanDao.addLoanDetails(loan);
//					logger.info("Loan details added successfully");
//				}
//			} catch (Exception e) {
//				logger.error(e.getMessage());
//				throw new LoanException(e.getMessage());
//
//			}
//			return loanId;
//
//		}
//	}
}
