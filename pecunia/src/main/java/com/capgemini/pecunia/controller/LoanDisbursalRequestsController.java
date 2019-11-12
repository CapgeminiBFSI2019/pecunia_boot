package com.capgemini.pecunia.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.exception.LoanDisbursalException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Loan;
import com.capgemini.pecunia.model.LoanDisbursal;
import com.capgemini.pecunia.service.LoanDisbursalService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
@RestController
public class LoanDisbursalRequestsController {
	@Autowired
	LoanDisbursalService loanDisbursalService;
	
	
	@CrossOrigin(origins = "http://localhost:4200")  
	@RequestMapping(value = "/loandisbursal/{menuOption}", method = RequestMethod.GET)
	@GetMapping(value = "/loandisbursal/{menuOption}")
	@ResponseBody
	public String loanRequests(
	  @PathVariable String menuOption) throws IOException {
		
		JsonArray jsonArray = new JsonArray();  
		Gson gson = new Gson();
		JsonObject dataResponse = new JsonObject();
		ArrayList<Loan> retrieveAll = new ArrayList<Loan>();
		String s = menuOption;
		
		if (s.equals("Retrieve all loan requests")) {
		try {
			retrieveAll = loanDisbursalService.retrieveAll();
			if (retrieveAll.size() > 0) {
				for (Loan loanReqs : retrieveAll) {
					jsonArray.add(gson.toJson(loanReqs, Loan.class));
				}
				dataResponse.addProperty("success", true);
				dataResponse.add("data", jsonArray);
			} else {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", "Error in connection");
			}
		} catch (PecuniaException | LoanDisbursalException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		} 
		}
		
		else if (s.equals("Show the loan requests to be rejected")) {

			try {
				retrieveAll = loanDisbursalService.rejectedLoanRequests();
				System.out.println("number of loan disbursal" + retrieveAll.size());
				if (retrieveAll.size() > 0) {
					for (Loan loanReqs : retrieveAll) {
						jsonArray.add(gson.toJson(loanReqs, Loan.class));
					}
					dataResponse.addProperty("success", true);
					dataResponse.add("data", jsonArray);
				} else {
					dataResponse.addProperty("success", true);
					dataResponse.addProperty("message", "No loan requests are pending");
				}
			} catch (PecuniaException | LoanDisbursalException e) {
				dataResponse.addProperty("success", false);
				dataResponse.addProperty("message", e.getMessage());
			} 
		}
		
		else if (s.equals("Show the loan requests to be accepted")) {
			try {
				retrieveAll = loanDisbursalService.approveLoan();
				System.out.println("number of loan disbursal" + retrieveAll.size());
				if (retrieveAll.size() > 0) {
					for (Loan loanReqs : retrieveAll) {
						jsonArray.add(gson.toJson(loanReqs, Loan.class));
					}
					dataResponse.addProperty("success", true);
					dataResponse.add("data", jsonArray);
				} else {
					dataResponse.addProperty("success", true);
					dataResponse.addProperty("message", "No loan requests are pending");
				}
			} catch (PecuniaException | LoanDisbursalException e) {
				dataResponse.addProperty("success", false);
				dataResponse.addProperty("message", e.getMessage());
			} 

		}
		
		
		return dataResponse.toString();
	
	}
		
	}
	


