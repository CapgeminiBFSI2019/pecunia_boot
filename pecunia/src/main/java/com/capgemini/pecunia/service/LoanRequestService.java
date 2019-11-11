package com.capgemini.pecunia.service;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.model.LoanRequest;

public interface LoanRequestService {
	public int createLoanRequest(LoanRequest loan) throws LoanException;

}
