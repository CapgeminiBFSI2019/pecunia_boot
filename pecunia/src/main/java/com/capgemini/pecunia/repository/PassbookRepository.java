package com.capgemini.pecunia.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.model.Transaction;



public interface PassbookRepository extends JpaRepository<Transaction, String>{

	 @Query("select t from Transaction t where t.accountId= ?1 AND t.transDate BETWEEN (SELECT lastUpdated from Account where accountId= ?1) and ?2")
	  List<Transaction> findById(String accountId, LocalDateTime currentDate);
	 
	 @Query("select t from Transaction t where t.accountId= ?1 AND t.transDate BETWEEN ?2 and ?3")
	 List<Transaction> getAccountSummary(String accountId, LocalDateTime localDateTime, LocalDateTime localDateTime2);
	 
}
