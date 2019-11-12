package com.capgemini.pecunia.service;

import org.springframework.stereotype.Service;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.model.LoanRequest;

@Service
public interface LoanRequestService {
	public int createLoanRequest(LoanRequest loan) throws LoanException;

}
