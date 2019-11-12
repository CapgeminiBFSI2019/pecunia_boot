package com.capgemini.pecunia.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.pecunia.model.Transaction;



public interface PassbookRepository extends JpaRepository<Transaction, String>{

	 @Query("select t from Transaction t where t.accountId= ?1 AND t.transDate BETWEEN (SELECT lastUpdated from Account where accountId= ?1) and ?2")
	  List<Transaction> findById(String accountId, LocalDateTime currentDate);
	 
	 @Query("UPDATE Account SET lastUpdated = ?1 WHERE accountId= ?2")
	 Boolean findByTime( LocalDateTime currentDate, String accountId);
}
