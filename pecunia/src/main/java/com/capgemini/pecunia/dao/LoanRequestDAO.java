package com.capgemini.pecunia.dao;

import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.LoanRequest;

@Repository
public interface LoanRequestDAO {
	public int addLoanDetails(LoanRequest loan) throws PecuniaException, LoanException;

}
