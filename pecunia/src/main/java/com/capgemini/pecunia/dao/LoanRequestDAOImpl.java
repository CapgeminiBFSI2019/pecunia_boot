package com.capgemini.pecunia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.LoanRequest;
import com.capgemini.pecunia.repository.LoanRequestRepository;


@Component
public class LoanRequestDAOImpl {

	@Autowired
	LoanRequestRepository loanRequestRepository ;
	public int addLoanDetails(LoanRequest loan) throws PecuniaException, LoanException {
		LoanRequest loanRequestEntity=new LoanRequest();
		loanRequestEntity.setAccountId(loan.getAccountId());
		loanRequestEntity.setAmount(loan.getAmount());
		loanRequestEntity.setCreditScore(loan.getCreditScore());
		loanRequestEntity.setEmi(loan.getEmi());	
		loanRequestEntity.setRoi(loan.getRoi());
		loanRequestEntity.setTenure(loan.getTenure());
		loanRequestEntity.setType(loan.getType());
		loanRequestEntity.setStatus(loan.getStatus());
		loanRequestEntity.setLoanId(loan.getLoanId());
		return 0;
		
		
	}
	}


