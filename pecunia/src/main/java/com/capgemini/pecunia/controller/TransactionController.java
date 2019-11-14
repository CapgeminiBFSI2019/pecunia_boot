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

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/debitCheque")

	/*******************************************************************************************************
	 * - Function Name : debitUsingCheque(@RequestBody Map<String, Object>
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Anish Basu - Creation Date :
	 * 02/11/2019 - Description : Debit Using Cheque
	 ********************************************************************************************************/

	public String debitUsingCheque(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();

		String accountNumber = requestData.get("accountNumber").toString();
		int debitChequeNumber = Integer.parseInt(requestData.get("debitChequeNumber").toString());
		String holderName = requestData.get("holderName").toString();
		double amount = Double.parseDouble(requestData.get("debitChequeAmount").toString());
		LocalDate chequeIssueDate = LocalDate.parse(requestData.get("issueDate").toString());
		String ifsc = requestData.get("ifsc").toString();

		Transaction debitChequeTransaction = new Transaction();
		debitChequeTransaction.setAmount(amount);
		debitChequeTransaction.setAccountId(accountNumber);

		Cheque debitCheque = new Cheque();
		debitCheque.setAccountNo(accountNumber);
		debitCheque.setHolderName(holderName);
		debitCheque.setIfsc(ifsc);
		debitCheque.setIssueDate(chequeIssueDate);
		debitCheque.setNum(debitChequeNumber);

		try {
			int transId = transactionService.debitUsingCheque(debitChequeTransaction, debitCheque);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount debited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	/*******************************************************************************************************
	 * - Function Name : creditUsingSlip(@RequestBody Map<String, Object>
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Arpan Mondal - Creation Date :
	 * 02/11/2019 - Description : Credit Using Slip
	 ********************************************************************************************************/

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/creditSlip")
	public String creditUsingSlip(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();
		String accountNum = requestData.get("accountNumber").toString();
		double amount = Double.parseDouble(requestData.get("creditSlipAmount").toString());
		Transaction creditSlipTransaction = new Transaction();
		creditSlipTransaction.setAmount(amount);
		creditSlipTransaction.setAccountId(accountNum);

		try {
			int transId = transactionService.creditUsingSlip(creditSlipTransaction);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount credited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/debitSlip")

	/*******************************************************************************************************
	 * - Function Name : debitUsingSlip(@RequestBody Map<String, Object>
	 * requestData) - Input Parameters : @RequestBody Map<String, Object>
	 * requestData - Return Type : String - Author : Anwesha Das - Creation Date :
	 * 02/11/2019 - Description : Debit Using Slip
	 ********************************************************************************************************/

	public String debitUsingSlip(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();
		String accountNumber = requestData.get("accountNumber").toString();
		double amount = Double.parseDouble(requestData.get("debitSlipAmount").toString());
		Transaction debitSlipTransaction = new Transaction();
		debitSlipTransaction.setAmount(amount);
		debitSlipTransaction.setAccountId(accountNumber);

		try {
			int transId = transactionService.debitUsingSlip(debitSlipTransaction);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("Transaction Id", transId);
			dataResponse.addProperty("message", "Amount debited.Trans Id is \t" + transId);

		} catch (TransactionException | PecuniaException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}
}
