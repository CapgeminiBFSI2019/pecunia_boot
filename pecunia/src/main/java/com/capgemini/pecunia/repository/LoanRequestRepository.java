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
	

	
	@Query(value = "SELECT SUM(emi) FROM Loan WHERE accountId = ?1")
	public double sumOfEmi(String accountId);
	
	
}

