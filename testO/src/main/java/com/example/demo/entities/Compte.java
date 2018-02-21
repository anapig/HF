package com.example.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Compte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCompte;
	@Email
	@NotEmpty
	private String login;
	@NotEmpty
	@Size(min=4)
	private String mdp;

	public Compte(String login, String mdp) {
		super();
		this.login = login;
		this.mdp = mdp;

	}

	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(long idCompte) {
		this.idCompte = idCompte;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

}
