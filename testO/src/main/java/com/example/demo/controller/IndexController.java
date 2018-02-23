package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.Compte;

@RequestMapping("/")
@Controller
public class IndexController {

	/*
	 * point d'entrer de l'application localhost:8080/
	 */ @GetMapping("/")
	public String index(Model model) {

		model.addAttribute("compte", new Compte());
		return "/index";
	}

	/*
	 * permet l'acces a la vue 'compte.html' qui permet l'ajout d'un nouveau compte
	 */	@RequestMapping("/compte")
	public String add(Model model) {

		model.addAttribute("compte", new Compte());
		return "/compte";
	}

}