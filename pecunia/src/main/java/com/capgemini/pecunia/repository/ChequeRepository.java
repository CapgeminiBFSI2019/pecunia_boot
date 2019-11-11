package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Cheque;

public interface ChequeRepository extends JpaRepository<Cheque, Integer>{

}
