package com.capgemini.pecunia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.pecunia.model.Transaction;



public interface PassbookRepository extends JpaRepository<Transaction, String>{

	 @Query("select * from TransactionEntity where accountId= ?1 AND date BETWEEN (SELECT lastUpdated from AccountEntity where accountId= ?1) and ?2")
	  List<Transaction> findById(String accountId);
}
