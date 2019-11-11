package com.capgemini.pecunia.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.exception.LoanException;
import com.capgemini.pecunia.model.LoanRequest;
import com.capgemini.pecunia.service.LoanRequestService;
import com.google.gson.JsonObject;

@RestController
public class LoanRequestController {
	@Autowired
	LoanRequest loan;
	@Autowired
	LoanRequestService loanService;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/LoanRequest")
	public String loanRequest(@RequestBody Map<String, Object> requestData) {
	
		JsonObject dataResponse = new JsonObject();

		String accountId = requestData.get("accountId").toString();

		double amount = Double.parseDouble(requestData.get("amount").toString());

		String type = requestData.get("type").toString();

		int tenure = (int) Double.parseDouble(requestData.get("tenure").toString());

		double roi = Double.parseDouble(requestData.get("roi").toString());

		String loanStatus = requestData.get("status").toString();

		int creditScore = Integer.parseInt(requestData.get("creditScore").toString());

		loan.setAccountId(accountId);
		loan.setAmount(amount);
		loan.setCreditScore(creditScore);
		loan.setStatus(loanStatus);
		loan.setType(type);
		loan.setTenure(tenure);
		loan.setRoi(roi);

		try {
			int loanId = loanService.createLoanRequest(loan);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Loan Id", loanId);
			dataResponse.addProperty("message", "Loan Request added succesfully with Loan Id \t" + loanId);

		} catch (LoanException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}
}
