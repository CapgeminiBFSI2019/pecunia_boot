package com.capgemini.pecunia.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.pecunia.model.Loan;

public interface LoanRequestRepository extends JpaRepository<Loan, Integer>{
	@Query("SELECT l FROM Loan l WHERE l.creditScore >= 670 AND l.status = 'Pending'")
	 public ArrayList<Loan> findByScoreAndStatus();
	
	@Query("SELECT l FROM Loan l WHERE l.creditScore >= 670")
	 public ArrayList<Loan> findByScore();
	
	@Query("SELECT l FROM Loan l WHERE l.creditScore < 670 AND l.status = 'Pending'")
	 public ArrayList<Loan> findRejectedByScoreAndStatus();
	
	@Modifying
	@Query("UPDATE Loan l SET l.status = ?1 WHERE loanId = ?2")
	public Loan updateStatusOfRequest(String status, Integer loanId);
	
	@Query(value = "SELECT SUM(emi) FROM loan WHERE accountId = ?1")
	public double sumOfEmi(String accountId);
	
	
}
//public static final String UPDATE_LOAN_STATUS = "UPDATE loan SET loan_status = ?  WHERE loan_id = ?"

//value = "SELECT sum(quantity) FROM Product" SELECT SUM(emi) FROM loan WHERE account_id = ?