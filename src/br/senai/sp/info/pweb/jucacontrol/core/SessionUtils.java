package br.senai.sp.info.pweb.jucacontrol.core;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.senai.sp.info.pweb.jucacontrol.models.Usuario;

@Component
public class SessionUtils {

	@Autowired
	private HttpSession session;
	
	public static final String CHAVE_USUARIO_LOGADO = "usuarioLogado";
	
	public Usuario getUsuarioLogado() {
		return (Usuario) session.getAttribute(CHAVE_USUARIO_LOGADO);
	}
	
	public void setUsuarioLogado(Usuario usuario) {
		session.setAttribute(CHAVE_USUARIO_LOGADO, usuario);
	}
	
	public boolean isUsuarioLogado() {
		return session.getAttribute(CHAVE_USUARIO_LOGADO) != null;
	}
	
	public void invalidarSessao() {
		session.invalidate();
	}
	
}
