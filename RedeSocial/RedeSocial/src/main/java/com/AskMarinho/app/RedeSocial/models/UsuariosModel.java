package com.AskMarinho.app.RedeSocial.models;

	import java.sql.Date;
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	//import javax.persistence.ManyToOne;
	import javax.persistence.Table;
	//import javax.persistence.Temporal;
	//import javax.persistence.TemporalType;
	import javax.validation.constraints.NotNull;
	import javax.validation.constraints.Size;
	//import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
	//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


	@Entity
	@Table(name = "usuarios")
	public class UsuariosModel {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id_usuario;
		
		@NotNull
		@Size(min = 3, max = 50, message = "Nome completo.")
		private String nome;
		
		@NotNull
		@Size(min = 5, max = 15, message = "Crie um username entre 5 e 15 caracteres.")
		private String nome_usuario;
		
		@NotNull
		@Size(min = 10, max = 50)
		private String email;
		
		@NotNull
		@Size(min = 8, max = 15, message = "Crie uma senha com no mínimo 8 caracteres.")
		private String senha;
		
		@NotNull
		private Date nascimento = Date.valueOf("AAAA/MM/DD");
		
		@NotNull
		@Size(min = 3, max = 50, message = "Digite seu gênero por extenso.")
		private String genero;
		
		@NotNull
		@Size(min = 11, max = 15, message = "Digite o DDI, o DDD e seu telefone.")
		private Long telefone;

		@Column(name = "biografia", length = 300)
		private String biografia;

		public long getId_usuario() {
			return id_usuario;
		}

		public void setId_usuario(long id_usuario) {
			this.id_usuario = id_usuario;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getNome_usuario() {
			return nome_usuario;
		}

		public void setNome_usuario(String nome_usuario) {
			this.nome_usuario = nome_usuario;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public Date getNascimento() {
			return nascimento;
		}

		public void setNascimento(Date nascimento) {
			this.nascimento = nascimento;
		}

		public String getGenero() {
			return genero;
		}

		public void setGenero(String genero) {
			this.genero = genero;
		}

		public Long getTelefone() {
			return telefone;
		}

		public void setTelefone(Long telefone) {
			this.telefone = telefone;
		}

		public String getBiografia() {
			return biografia;
		}

		public void setBiografia(String biografia) {
			this.biografia = biografia;
		}

		
	}
