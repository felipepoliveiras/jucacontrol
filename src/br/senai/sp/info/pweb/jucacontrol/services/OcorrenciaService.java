package br.senai.sp.info.pweb.jucacontrol.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.senai.sp.info.pweb.jucacontrol.dao.OcorrenciaDAO;
import br.senai.sp.info.pweb.jucacontrol.exceptions.EntidadeNaoEncontradaException;
import br.senai.sp.info.pweb.jucacontrol.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.jucacontrol.models.Ocorrencia;

@Service
public class OcorrenciaService {
	
	@Autowired
	private OcorrenciaDAO ocorrenciaDAO;
	
	/**
	 * Realiza a altera��o de uma ocorrencia de ocorr�ncia
	 * @param id - O id da ocorrencia de ocorr�ncia que ser� alterada
	 * @param ocorrencia - Objeto com os novos dados da ocorrencia de ocorr�ncia
	 * @param bindingResult - Objeto com erros de valida��o
	 * @return
	 * @throws ValidacaoException - Caso a cateogira possua erros de valida��o
	 * @throws EntidadeNaoEncontradaException  - Caso a ocorrencia alterada n�o exista
	 */
	public Ocorrencia alterar(Long id, Ocorrencia ocorrencia, BindingResult bindingResult) throws ValidacaoException, EntidadeNaoEncontradaException {
		
		//Verifica se a ocorrencia possui erros de valida��o
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Realiza a busca da ocorrencia atrav�s do seu ID
		Ocorrencia ocorrenciaBuscada = buscarPorId(id);
		
		//Passa os par�metros da ocorrencia para a ocorrencia buscada
		BeanUtils.copyProperties(ocorrencia, ocorrenciaBuscada, "id");
		
		//Altera a ocorrencia no banco de dados
		ocorrenciaDAO.alterar(ocorrenciaBuscada);
		
		return ocorrencia;
	}
	
	/**
	 * Busca uma determinada ocorrencia atrav�s do seu ID
	 * @param id
	 * @return
	 * @throws EntidadeNaoEncontradaException
	 */
	public Ocorrencia buscarPorId(Long id) throws EntidadeNaoEncontradaException {
		Ocorrencia ocorrencia = ocorrenciaDAO.buscar(id);
		
		//Verifica se a ocorrencia existe
		if(ocorrencia == null){
			throw new EntidadeNaoEncontradaException();
		}
		
		return ocorrencia;
	}
	
	/**
	 * Busca todas as ocorrencias de ocorr�ncia cadastrada no banco de dados
	 * @return
	 */
	public List<Ocorrencia> buscarTodos(){
		return ocorrenciaDAO.buscarTodos();
	}
	

	/**
	 * Realiza o cadastro da ocorrencia
	 * @param ocorrencia - A ocorrencia que ser� cadastrada
	 * @param bindingResult - Objeto com poss�veis erros de valida��o
	 * @return
	 * @throws ValidacaoException
	 */
	public Ocorrencia cadastrar(Ocorrencia ocorrencia, BindingResult bindingResult) throws ValidacaoException {
		
		//Verifica os poss�veis erros nos campos da ocorrencia de ocorr�ncia
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Realiza o cadastro
		ocorrenciaDAO.inserir(ocorrencia);
		
		return ocorrencia;	
	}

	/**
	 * Deleta uma ocorrencia do banco de dados
	 * @param id
	 * @throws EntidadeNaoEncontradaException - Caso a ocorrencia a ser deletada n�o exista
	 */
	public void deletar(Long id) throws EntidadeNaoEncontradaException {
		ocorrenciaDAO.deletar(buscarPorId(id));
	}

}
