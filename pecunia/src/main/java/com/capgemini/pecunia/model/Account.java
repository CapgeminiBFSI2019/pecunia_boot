package com.capgemini.pecunia.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

@Table(name = "account")
public class Account {
	@Id
	@Column(name = "account_id")
	private String accountId;
	@Column(name = "customer_id")
	private String customerId;
	@Column(name = "branch_id")
	private String branchId;
	@Column(name = "type")
	private String type;
	@Column(name = "status")
	private String status;
	@Column(name = "balance")
	private double balance;
	@Column(name = "interest")
	private double interest;
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;
	

	public Account() {

	}

	public Account(String accountId, String customerId, String branchId, String type, String status, double balance,
			double interest, LocalDateTime lastUpdated) {
		super();
		this.accountId = accountId;
		this.customerId = customerId;
		this.branchId = branchId;
		this.type = type;
		this.status = status;
		this.balance = balance;
		this.interest = interest;
		this.lastUpdated = lastUpdated;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
