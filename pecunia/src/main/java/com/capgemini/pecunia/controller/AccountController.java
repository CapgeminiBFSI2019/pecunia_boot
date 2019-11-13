package com.capgemini.pecunia.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.pecunia.exception.AccountException;
import com.capgemini.pecunia.exception.PecuniaException;
import com.capgemini.pecunia.model.Account;
import com.capgemini.pecunia.model.Address;
import com.capgemini.pecunia.model.Customer;
import com.capgemini.pecunia.service.AccountManagementService;
import com.capgemini.pecunia.util.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class AccountController {

	@Autowired
	AccountManagementService accManagementService;

	Account account = new Account();
	Customer customer = new Customer();
	Address address = new Address();

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updateName")
	public String updateCustomerName(@RequestBody Map<String, Object> requestData) {
		
		JsonObject dataResponse = new JsonObject();
		String accountId = requestData.get("accountId").toString();
		String custName = requestData.get("name").toString();

		account.setAccountId(accountId);
		customer.setName(custName);
		boolean updated = false;

		try {
			updated = accManagementService.updateCustomerName(account, customer);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_NAME_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updateContact")
	public String updateCustomerContact(@RequestBody Map<String, Object> requestData) {

		JsonObject dataResponse = new JsonObject();
		String accountId = requestData.get("accountId").toString();
		String custContact = requestData.get("contact").toString();

		account.setAccountId(accountId);
		customer.setContact(custContact);
		boolean updated = false;

		try {
			updated = accManagementService.updateCustomerContact(account, customer);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_CONTACT_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/updateAddress")
	public String updateCustomerAddress(@RequestBody Map<String, Object> requestData) {

		JsonObject dataResponse = new JsonObject();
		String accountId = requestData.get("accountId").toString();
		String line1 = requestData.get("line1").toString();
		String line2 = requestData.get("line2").toString();
		String city = requestData.get("city").toString();
		String state = requestData.get("state").toString();
		String country = requestData.get("country").toString();
		String zipcode = requestData.get("zipcode").toString();

		account.setAccountId(accountId);
		address.setAddressLine1(line1);
		address.setAddressLine2(line2);
		address.setCity(city);
		address.setCountry(country);
		address.setState(state);
		address.setZipcode(zipcode);

		boolean updated = false;

		try {
			updated = accManagementService.updateCustomerAddress(account, address);
			if (updated) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.UPDATE_ADDRESS_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/addAccount")
	public String addAccount(@RequestBody Map<String, Object> requestData) {

		JsonObject dataResponse = new JsonObject();
		String name = requestData.get("name").toString();
		String gender = requestData.get("gender").toString();
		if ("Female".equalsIgnoreCase(gender)) {
			customer.setGender("F");
		} else {
			customer.setGender("M");
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateofbirth = requestData.get("dob").toString();
		String contact = requestData.get("contact").toString();
		String aadhar = requestData.get("aadhar").toString();
		String pan = requestData.get("pan").toString();
		String line1 = requestData.get("line1").toString();
		String line2 = requestData.get("line2").toString();
		String city = requestData.get("city").toString();
		String state = requestData.get("state").toString();
		String country = requestData.get("country").toString();
		String zipcode = requestData.get("zipcode").toString();
		String accounttype = requestData.get("accountType").toString();
		String branchid = requestData.get("branchId").toString();
		double accountbalance = Double.parseDouble(requestData.get("balance").toString());
		double accountinterest = Double.parseDouble(requestData.get("interest").toString());
		address.setAddressLine1(line1);
		address.setAddressLine2(line2);
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		address.setZipcode(zipcode);
		customer.setAadhar(aadhar);
		customer.setContact(contact);
		customer.setDob(LocalDate.parse(dateofbirth, dateTimeFormatter));
		customer.setName(name);
		customer.setPan(pan);
		account.setType(accounttype);
		account.setBranchId(branchid);
		account.setBalance(accountbalance);
		account.setInterest(accountinterest);
		try {
			String created = accManagementService.addAccount(customer, address, account);
			if (created != null) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("Account Id", created);
				dataResponse.addProperty("message", "Account has been created. Account Id is \t" + created);

			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());

		}
		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(path = "/deleteAccount")
	public String deleteAccount(@RequestBody Map<String, Object> requestData) {
		JsonObject dataResponse = new JsonObject();

		String accountId = requestData.get("accountId").toString();

		account.setAccountId(accountId);
		try {
			boolean isDeleted = accManagementService.deleteAccount(account);
			if (isDeleted) {
				dataResponse.addProperty("success", true);
				dataResponse.addProperty("message", Constants.DELETE_ACCOUNT_SUCCESSFUL);
			}
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}

		return dataResponse.toString();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path = "/accountDetail/{id}")
	public String showAccountDetails(@PathVariable("id") String id) {
		Gson gson = new Gson();
		JsonObject dataResponse = new JsonObject();
		String accountId = id;

		account.setAccountId(accountId);
		try {

			account = accManagementService.showAccountDetails(account);
			String jsonInString = gson.toJson(account);
			dataResponse.addProperty("success", true);
			dataResponse.addProperty("data", jsonInString);
			dataResponse.addProperty("message", accountId.toString());
		} catch (PecuniaException | AccountException e) {
			dataResponse.addProperty("success", false);
			dataResponse.addProperty("message", e.getMessage());
		}
		return dataResponse.toString();
	}

}
