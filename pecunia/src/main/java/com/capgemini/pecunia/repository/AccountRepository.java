package com.capgemini.pecunia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Account;


public interface AccountRepository extends JpaRepository<Account, String>{

	Optional<Account> findByAccountIdLike(String accountId);

}
