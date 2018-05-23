package br.senai.sp.info.pweb.jucacontrol.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import br.senai.sp.info.pweb.jucacontrol.dao.UsuarioDAO;
import br.senai.sp.info.pweb.jucacontrol.models.Usuario;
import br.senai.sp.info.pweb.jucacontrol.utils.JwtUtils;

@Component
public class JwtFilter extends GenericFilterBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		//Verifica se veio token do Header
		String token = req.getHeader("Authorization");
		
		if(token != null) {
			
			if(token.matches("(Bearer) .*")) {
				
				//Pega o token
				token = token.split(" ")[1];
				
				//Pega o usuario do token
				try {
					Usuario usuarioToken = JwtUtils.obterUsuarioDoTokenAutenticacao(token);
					SecurityContextHolder.getContext().setAuthentication(usuarioToken);
					
				} catch (Exception e) {
					res.setStatus(401);
				}
			}else {
				System.err.println("Token não esta no formado Bearer");
			}
		}else {
			System.err.println("Authorization não informado");
		}
		
		chain.doFilter(req, res);
		
	}

}
