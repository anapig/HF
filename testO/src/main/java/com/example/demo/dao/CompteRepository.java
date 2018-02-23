package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long> {

	public Compte findByLoginAndMdp(String login, String mdp);
	
	public Compte findByLogin(String login);
	
	

}