package com.capgemini.pecunia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
//@NamedQueries({
//		@NamedQuery(name = "LoginEntity.getsecret_keyByusername", query = "FROM LoginEntity where username =:email") ,
//		@NamedQuery(name = "LoginEntity.getpasswordByusername", query = "FROM LoginEntity where username =:email") })

@Table(name = "login")
public class Login {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email")
	private String username;
	@Column(name = "password")
	private String password;

	@Column(name = "secret_key")
	private String secretKey;

	public Login() {

	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
