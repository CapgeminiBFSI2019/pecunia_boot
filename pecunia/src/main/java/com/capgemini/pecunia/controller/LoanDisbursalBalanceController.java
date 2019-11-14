package com.capgemini.pecunia.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;
import com.capgemini.pecunia.service.LoanDisbursalService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
public class LoanDisbursalBalanceController {
	@Autowired
	LoanDisbursalService loanDisbursalService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path = "/loandisbursalbalanceupdation")
	public String updateBalance() throws IOException {
		ArrayList<Loan> retrieveAccepted = new ArrayList<Loan>();
		try {
			retrieveAccepted = loanDisbursalService.approveLoanWithoutStatus();
		} catch (PecuniaException | LoanDisbursalException e1) {

			e1.printStackTrace();
		}

		JsonArray jsonArray = new JsonArray(); // Creating json object
		Gson gson = new Gson();
		JsonObject dataResponse = new JsonObject();
		ArrayList<LoanDisbursal> retrieveLoanDisbursedData = new ArrayList<LoanDisbursal>();
		try {
			retrieveLoanDisbursedData = loanDisbursalService.approvedLoanList();
		} catch (PecuniaException | LoanDisbursalException e1) {
			e1.printStackTrace();
		}

		try {
			ArrayList<String> msg = loanDisbursalService.updateExistingBalance(retrieveAccepted,
					retrieveLoanDisbursedData);
			if (msg.size() > 0) {
				for (String jsonString : msg) {
					jsonArray.add(gson.toJson(jsonString, String.class));
				}
				dataResponse.addProperty("success", true);
				dataResponse.add("data", jsonArray);
			}

		} catch (PecuniaException | LoanDisbursalException | TransactionException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();

	}
}
