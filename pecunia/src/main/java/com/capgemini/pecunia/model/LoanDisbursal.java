package com.capgemini.pecunia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loan_disbursal")

public class LoanDisbursal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_disbursal_id")
	private int loanDisbursalId;
	@Column(name = "loan_id")
	private int loanId;
	@Column(name = "account_id")
	private String accountId;
	@Column(name = "disbursed_amount")
	private double disbursedAmount;
	@Column(name = "due_amount")
	private double dueAmount;
	@Column(name = "emi_to_be_paid")
	private double numberOfEmiToBePaid;
	@Column(name = "loan_type")
	private String loanType;

	public LoanDisbursal() {

	}

	public int getLoanDisbursalId() {
		return loanDisbursalId;
	}

	public void setLoanDisbursalId(int loanDisbursalId) {
		this.loanDisbursalId = loanDisbursalId;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public double getDisbursedAmount() {
		return disbursedAmount;
	}

	public void setDisbursedAmount(double disbursedAmount) {
		this.disbursedAmount = disbursedAmount;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public double getNumberOfEmiToBePaid() {
		return numberOfEmiToBePaid;
	}

	public void setNumberOfEmiToBePaid(double numberOfEmiToBePaid) {
		this.numberOfEmiToBePaid = numberOfEmiToBePaid;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

}
