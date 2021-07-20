package com.AskMarinho.app.RedeSocial.models;

public class UserLogin {
	
	private Long idUser;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String token;
	
	private String urlImage;
	
	private String tipo;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public String getTipo() {
		return tipo;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
