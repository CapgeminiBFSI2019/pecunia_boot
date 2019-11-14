package com.capgemini.pecunia.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.ErrorConstants;
import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanRequest;
import com.capgemini.pecunia.repository.LoanRequestRepository;

@Component
public class LoanRequestDAOImpl implements LoanRequestDAO {

	private static final Logger logger = LoggerFactory.getLogger(LoanDisbursalDAO.class);

	@Autowired
	LoanRequestRepository loanRequestRepository;

	@Override
	public int addLoanDetails(LoanRequest loan) throws PecuniaException, LoanException {
		int loanId = 0;
		try {
			Loan loanRequestEntity = new Loan();
			loanRequestEntity.setAccountId(loan.getAccountId());
			loanRequestEntity.setAmount(loan.getAmount());
			loanRequestEntity.setCreditScore(loan.getCreditScore());
			loanRequestEntity.setEmi(loan.getEmi());
			loanRequestEntity.setRoi(loan.getRoi());
			loanRequestEntity.setTenure(loan.getTenure());
			loanRequestEntity.setType(loan.getType());
			loanRequestEntity.setStatus(loan.getStatus());
			loanRequestEntity = loanRequestRepository.save(loanRequestEntity);
			loanId = loanRequestEntity.getLoanId();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new PecuniaException(ErrorConstants.LOAN_ADD_ERROR);
		}
		return loanId;

	}
}
