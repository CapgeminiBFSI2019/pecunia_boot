package com.capgemini.pecunia.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.exception.TransactionException;
import com.capgemini.pecunia.model.Cheque;
import com.capgemini.pecunia.model.Transaction;
import com.capgemini.pecunia.service.TransactionService;
import com.google.gson.JsonObject;

@RestController
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	
	/*******************************************************************************************************
	 * - Function Name : creditUsingCheque(@RequestBody Map<String,
	 * Object>)requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Rohan patil - Creation Date :
	 * 02/11/2019 - Description : Credit Using Cheque
	 ********************************************************************************************************/

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/creditCheque")
	public String creditUsingCheque(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();
		String payeeAccountNumber = requestData.get("payeeAccountNumber").toString();
		String beneficiaryAccountNumber = requestData.get("beneficiaryAccountNumber").toString();
		String chequeNumber = requestData.get("creditChequeNumber").toString();
		String payeeName = requestData.get("payeeName").toString();
		double amount = Double.parseDouble(requestData.get("creditChequeAmount").toString());
		LocalDate chequeIssueDate = LocalDate.parse(requestData.get("creditChequeIssueDate").toString());
		String bankName = requestData.get("bankName").toString();
		String ifsc = requestData.get("payeeIfsc").toString();

		System.out.println(payeeAccountNumber + "\n" + beneficiaryAccountNumber + "\n" + chequeNumber + "\n" + payeeName
				+ "\n" + amount + "\n" + chequeIssueDate + "\n" + bankName + "\n" + ifsc);
		
		Transaction creditChequeTransaction = new Transaction();
		creditChequeTransaction.setAmount(amount);
		creditChequeTransaction.setAccountId(beneficiaryAccountNumber);
		creditChequeTransaction.setTransTo(beneficiaryAccountNumber);
		creditChequeTransaction.setTransFrom(payeeAccountNumber);

		Cheque creditCheque = new Cheque();
		creditCheque.setAccountNo(payeeAccountNumber);
		creditCheque.setHolderName(payeeName);
		creditCheque.setIfsc(ifsc);
		creditCheque.setIssueDate(chequeIssueDate);
		creditCheque.setNum(Integer.parseInt(chequeNumber));
		creditCheque.setBankName(bankName);

		try {
			int transId = transactionService.creditUsingCheque(creditChequeTransaction, creditCheque);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount credited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}
}