package com.capgemini.pecunia.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Login;

public interface LoginRepository extends JpaRepository<Login, String>{
	
}

