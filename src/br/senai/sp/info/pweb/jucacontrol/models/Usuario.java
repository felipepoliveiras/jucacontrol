package br.senai.sp.info.pweb.jucacontrol.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.DigestUtils;

@Entity
@Table(name = "usuario")
public class Usuario implements Authentication{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "{NotNull}")
	private TiposUsuario tipo = TiposUsuario.COMUM;
	
	@Column(length = 30, nullable = false, unique = false)
	@Size(min = 1, max = 30, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String nome;
	
	@Column(length = 50, nullable = false, unique = false)
	@Size(min = 1, max = 50, message = "{Size}")
	@NotNull
	private String sobrenome;
	
	@Column(length = 120, nullable = false, unique = true)
	@Email(message = "{Email}")
	@NotNull(message = "{NotNull}")
	private String email;
	
	@Column(length = 64, nullable = false, unique = false)
	@Size(min = 1, max = 64, message = "{Size}")
	@NotNull(message = "{NotNull}")
	private String senha;
	
	@Transient
	private String caminhoFoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
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

	public TiposUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TiposUsuario tipo) {
		this.tipo = tipo;
	}
	
	public void hashearSenha() {
		this.senha = DigestUtils.md5DigestAsHex(this.senha.getBytes());
	}
	
	public String getCaminhoFoto() {
		return caminhoFoto;
	}
	
	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> autorizacoes = new ArrayList<>(2);
		
		if(this.getTipo() == TiposUsuario.ADMINISTRADOR) {
			autorizacoes.add(new SimpleGrantedAuthority("ROLE_ADM"));
		}
		
		autorizacoes.add(new SimpleGrantedAuthority("ROLE_COMUM"));
		
		return autorizacoes;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
}
