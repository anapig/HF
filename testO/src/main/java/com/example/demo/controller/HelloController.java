package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Photo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CompteRepository;
import com.example.demo.entities.Compte;

/* HelloController est cree par l'injection 
 * d'un objet Facebook dans le constructeur.
 * l'objet facebook est une reference à 
 * 'Spring Social’s Facebook API binding'.*/

@RestController
@CrossOrigin("*")

public class HelloController {

	@Autowired
	CompteRepository compteRepository;
	Compte compte = null;
	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	private Connection<Facebook> connection;

	public HelloController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}

	/*permet de verifier l'existance d'un compte
	 * accessible depuis 'localhost:8080/login' , par la methode POST*/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletResponse response, Compte c, BindingResult BindingResult) throws IOException {
		if (BindingResult.hasErrors())
			response.sendRedirect("/");
		else {
			compte = compteRepository.findByLoginAndMdp(c.getLogin(), c.getMdp());
			if (compte != null)
				response.sendRedirect("/helloFb");
			else
				response.sendRedirect("/");
		}
	}

	/*
	 * permet de persister un nouveau compte dans la base de donnee 
	 * elle est accessible depuis 'localhost:8080/add' , par la methode POST
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addCompte(@Valid Compte compte, BindingResult bindingResult, HttpServletResponse response)
			throws IOException {
		Compte c = compteRepository.findByLogin(compte.getLogin());
		if (bindingResult.hasErrors()|| c!=null) {
			response.sendRedirect("/compte");
		} else {
			compteRepository.save(compte);
			response.sendRedirect("/");
		}
	}

	/*
	 * accessible depuis 'localhost:8080/helloFb' cette methode verifie dans un
	 * premier temps si lutilisateur est deja authentifier sur l'application, si oui
	 * il verifie que l'utilisateur a etablie une connection avec facebook le cas
	 * echeant l'utilisateur est rediriger vers 'ConnectController' pour que le
	 * process se declenche de nouveau.
	 */
	@GetMapping("/helloFb")
	public void helloFacebook(HttpServletResponse response) throws IOException {
		if (compte != null) {
			connection = connectionRepository.findPrimaryConnection(Facebook.class);
			if (connection == null)
				response.sendRedirect("/connect/facebook");
			else
				response.sendRedirect("http://localhost:4200/");
		} else
			response.sendRedirect("/");
	}

	/*
	 * cette fontion permet de recuperer les albums de l'utilisateur connecté . elle
	 * est accessible depuis 'localhost:8080/albums'
	 */
	@RequestMapping("/albums")
	public List<Album> getAlbums() {
		return facebook.mediaOperations().getAlbums();
	}

	/*
	 * cette fontion permet de recuperer les photos d'un album donné de
	 * l'utilisateur connecté elle est accessible depuis
	 * 'localhost:8080/photos/{albumId}'
	 */
	@RequestMapping(value = "/photos/{albumId}", produces = "application/json")
	public List<Photo> getPhotos(@PathVariable String albumId) {
		List<Photo> photos = new ArrayList<>();
		photos = facebook.mediaOperations().getPhotos(albumId);

		return photos;
	}

}
