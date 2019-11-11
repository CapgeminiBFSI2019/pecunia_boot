package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.LoanDisbursal;

public interface LoanDisbursalRepository extends JpaRepository<LoanDisbursal, Integer> {

}
