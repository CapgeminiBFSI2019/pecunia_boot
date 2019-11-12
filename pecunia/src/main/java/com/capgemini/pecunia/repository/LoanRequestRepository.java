package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.LoanRequest;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {

}
	