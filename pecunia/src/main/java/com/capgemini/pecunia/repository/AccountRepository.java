package com.capgemini.pecunia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.pecunia.model.Account;


public interface AccountRepository extends JpaRepository<Account, String>{

	List<Account> findByAccountIdLikeOrderByAccountIdDesc(String string);

//	@Query("SELECT account FROM Account account WHERE account.accountId LIKE ?1 ORDER BY account.accountId DESC LIMIT 1")
//	Optional<Account> findByAccountIdLike(String accountId);


}
