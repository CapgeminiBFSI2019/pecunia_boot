package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
