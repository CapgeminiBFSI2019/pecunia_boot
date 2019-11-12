package com.capgemini.pecunia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loan")

public class LoanRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_Id")
	private int loanId;
	@Column(name = "account_Id")
	private String accountId;
	@Column(name = "amount")
	private double amount;
	@Column(name = "type")
	private String type;
	@Column(name = "tenure")
	private int tenure;
	@Column(name = "roi")
	private double roi;
	@Column(name = "loan_status")
	private String status;
	@Column(name = "emi")
	private double emi;
	@Column(name = "credit_Score")
	private int creditScore;

	public LoanRequest() {

	}

	public LoanRequest(int loanId, String accountId, double amount, String type, int tenure, double roi, String status,
			double emi, int creditScore) {
		super();
		this.loanId = loanId;
		this.accountId = accountId;
		this.amount = amount;
		this.type = type;
		this.tenure = tenure;
		this.roi = roi;
		this.status = status;
		this.emi = emi;
		this.creditScore = creditScore;
	}

	public double getEmi() {
		return emi;
	}

	public void setEmi(double emi) {
		this.emi = emi;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the tenure
	 */
	public int getTenure() {
		return tenure;
	}

	/**
	 * @param tenure the tenure to set
	 */
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	/**
	 * @return the roi
	 */
	public double getRoi() {
		return roi;
	}

	/**
	 * @param roi the roi to set
	 */
	public void setRoi(double roi) {
		this.roi = roi;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the creditScore
	 */
	public int getCreditScore() {
		return creditScore;
	}

	/**
	 * @param creditScore the creditScore to set
	 */
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

}
	