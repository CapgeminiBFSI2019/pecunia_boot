package com.capgemini.pecunia.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trans_id")
	private int id;

	@Column(name = "account_id")
	private String accountId;

	@Column(name = "type")
	private String type;

	@Column(name = "amount")
	private double amount;

	@Column(name = "trans_option")
	private String option;

	@Column(name = "date")
	private LocalDateTime transDate;

	@Column(name = "cheque_id")
	private int chequeId;

	@Column(name = "trans_from")
	private String transFrom;

	@Column(name = "trans_to")
	private String transTo;

	@Column(name = "trans_closing_balance")
	private double closingBalance;
	
	public Transaction() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public LocalDateTime getTransDate() {
		return transDate;
	}

	public void setTransDate(LocalDateTime transDate) {
		this.transDate = transDate;
	}

	public int getChequeId() {
		return chequeId;
	}

	public void setChequeId(int chequeId) {
		this.chequeId = chequeId;
	}

	public String getTransFrom() {
		return transFrom;
	}

	public void setTransFrom(String transFrom) {
		this.transFrom = transFrom;
	}

	public String getTransTo() {
		return transTo;
	}

	public void setTransTo(String transTo) {
		this.transTo = transTo;
	}

	public double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}
	
	
}
