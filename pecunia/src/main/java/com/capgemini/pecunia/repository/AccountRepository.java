package com.capgemini.pecunia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Account;


public interface AccountRepository extends JpaRepository<Account, String>{

}
