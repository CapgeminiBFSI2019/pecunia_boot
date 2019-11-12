

package com.capgemini.pecunia.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.capgemini.pecunia.exception.PassbookException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.service.PassbookMaintenanceService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
@RestController
public class PassbookController {
	
	@Autowired
	Transaction accountSummary;
	@Autowired
	PassbookMaintenanceService Passbook;
	@Autowired
	PassbookMaintenanceService account;
	@Autowired
	Transaction updatePassbook;
	
	@CrossOrigin(origins = "http://localhost:4200")  
	@PostMapping(path = "/accountSummary")
	
	/*******************************************************************************************************
	 * - Function Name : accountSummary(@RequestBody Map<String, Object> requestData) 
	 * - Input Parameters : @RequestBody Map<String, Object> requestData
	 * - Return Type : String
	 * - Author : Rishav Dev
	 * - Creation Date : 02/11/2019 
	 * - Description : Provides the account summary
	 ********************************************************************************************************/
	
	public String accountSummary(@RequestBody Map<String, Object> requestData) {
		


		String accountID = requestData.get("accountID").toString();
		LocalDate startDate = LocalDate.parse(requestData.get("startDate").toString());
		LocalDate endDate = LocalDate.parse(requestData.get("endDate").toString());
				
		JsonArray jsonArray = new JsonArray();  
	
		Gson gson = new Gson();
	
		JsonObject dataResponse = new JsonObject();


		try {
			List<Transaction> accountSumm = account.accountSummary(accountID, startDate, endDate);
			if (accountSumm.size() > 0) {
				for (Transaction transaction : accountSumm) {
					jsonArray.add(gson.toJson(transaction, Transaction.class));
				}	
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("message", "Account Summary \t");
			dataResponse.add("data", jsonArray);
			} else {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", "No summary to be displayed");
			}
			
		} catch (PassbookException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
		
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updatePassbook")
	
	/*******************************************************************************************************
	 * - Function Name : updatePassbook(@RequestBody Map<String, Object> requestData) 
	 * - Input Parameters : @RequestBody Map<String, Object> requestData
	 * - Return Type : String 
	 * - Author : Mansi Agarwal
	 * - Creation Date :02/11/2019 
	 * - Description : Update transaction details in passbook
	 ********************************************************************************************************/
	
	public String updatePassbook(@RequestBody Map<String, Object> requestData) {
		
		String accountID = requestData.get("accountID").toString();
				
		JsonArray jsonArray = new JsonArray();
	
		Gson gson = new Gson();
	
		JsonObject dataResponse = new JsonObject();


		try {
			List<Transaction> passbookUpdate = account.updatePassbook(accountID);
			if (passbookUpdate.size() > 0) {
				for (Transaction transaction : passbookUpdate) {
					jsonArray.add(gson.toJson(transaction, Transaction.class));
				}
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("message", "Passbook \t");
			dataResponse.add("data", jsonArray);
			} else {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", "No recent transaction to be displayed");
			}
			
		} catch (PassbookException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
		
		
	}
	
	
	
}
