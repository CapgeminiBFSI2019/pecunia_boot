package com.capgemini.pecunia.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cheque")
public class Cheque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cheque_id")
	private int id;

	@Column(name = "cheque_num")
	private int num;

	@Column(name = "account_num")
	private String accountNo;

	@Column(name = "cheque_holder_name")
	private String holderName;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "IFSC")
	private String ifsc;

	@Column(name = "issue_date")
	private LocalDate issueDate;

	@Column(name = "status")
	private String status;

	public Cheque() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
