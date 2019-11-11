package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Address;

public interface AddressRepository extends JpaRepository<Address, String>{

}
