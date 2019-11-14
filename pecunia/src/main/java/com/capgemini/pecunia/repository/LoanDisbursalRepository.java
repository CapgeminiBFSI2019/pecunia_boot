package com.capgemini.pecunia.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.pecunia.model.LoanDisbursal;

public interface LoanDisbursalRepository extends JpaRepository<LoanDisbursal, Integer> {
	@Modifying
	@Query("UPDATE LoanDisbursal l SET l.dueAmount = ?1, l.numberOfEmiToBePaid = ?2 WHERE l.accountId = ?3")
	public void updateLoanDisbursal(double dueAmount, double numberOfEmiToBePaid, String accountId);

	@Query("SELECT DISTINCT accountId FROM LoanDisbursal")
	ArrayList<String> uniqueAccIds();
}
