package br.senai.sp.info.pweb.jucacontrol.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.senai.sp.info.pweb.jucacontrol.core.SessionUtils;
import br.senai.sp.info.pweb.jucacontrol.models.TiposUsuario;

@Component
public class AutenticacaoPorSessaoInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private SessionUtils session;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		boolean necessitaAutenticacao = request.getRequestURI().contains("/app");
		if(necessitaAutenticacao) {
			
			//Verifica se o usu�rio n�o est� logado
			if(!session.isUsuarioLogado()) {
				response.sendError(401);
				return false;
			}
			
			//Verifica se a url necessita de usu�rio administrador
			boolean necessitaSerAdm = request.getRequestURI().contains("/adm");
			if(necessitaSerAdm && session.getUsuarioLogado().getTipo() != TiposUsuario.ADMINISTRADOR) {
				response.sendError(403);
				return false;
			}
		}
		
		//Se n�o passou nos filtros acima, libera acesso
		return true;
	}

}
