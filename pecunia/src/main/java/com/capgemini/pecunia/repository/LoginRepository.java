package com.capgemini.pecunia.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.pecunia.model.Login;

public interface LoginRepository extends JpaRepository<Login, Integer>{

	Optional<Login> findById(String secretKey);
}

