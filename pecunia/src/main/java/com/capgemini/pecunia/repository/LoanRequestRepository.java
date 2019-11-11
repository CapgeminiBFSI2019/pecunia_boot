package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Transaction;

public interface LoanRequestRepository extends JpaRepository<Transaction, Integer>{

}
