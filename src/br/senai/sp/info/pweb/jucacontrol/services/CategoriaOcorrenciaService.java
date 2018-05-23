package br.senai.sp.info.pweb.jucacontrol.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.senai.sp.info.pweb.jucacontrol.dao.CategoriaOcorrenciaDAO;
import br.senai.sp.info.pweb.jucacontrol.exceptions.EntidadeNaoEncontradaException;
import br.senai.sp.info.pweb.jucacontrol.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.jucacontrol.models.CategoriaOcorrencia;

@Service
public class CategoriaOcorrenciaService {
	
	@Autowired
	private CategoriaOcorrenciaDAO categoriaOcorrenciaDAO;
	
	/**
	 * Dispara uma {@link ValidacaoException} caso o nome da categoria j� esteja sendo utilizada
	 * @param nome
	 * @param bindingResult
	 * @throws ValidacaoException
	 */
	public void tratarNomeRepetido(String nome, BindingResult bindingResult) throws ValidacaoException {
		
		//Realiza a busca pelo nome
		if(!categoriaOcorrenciaDAO.buscarPorCampo("nome", nome).isEmpty()) {
			bindingResult.addError(new FieldError("categoriaOcorrencia", "nome", "O nome '" + nome + "' j� est� sendo utilizado"));
			throw new ValidacaoException();
		}
		
	}
	
	/**
	 * Realiza a altera��o de uma categoria de ocorr�ncia
	 * @param id - O id da categoria de ocorr�ncia que ser� alterada
	 * @param categoriaOcorrencia - Objeto com os novos dados da categoria de ocorr�ncia
	 * @param bindingResult - Objeto com erros de valida��o
	 * @return
	 * @throws ValidacaoException - Caso a cateogira possua erros de valida��o
	 * @throws EntidadeNaoEncontradaException  - Caso a categoria alterada n�o exista
	 */
	public CategoriaOcorrencia alterar(Long id, CategoriaOcorrencia categoriaOcorrencia, BindingResult bindingResult) throws ValidacaoException, EntidadeNaoEncontradaException {
		
		//Verifica se a categoria possui erros de valida��o
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Realiza a busca da categoria atrav�s do seu ID
		CategoriaOcorrencia categoriaOcorrenciaBuscada = buscarPorId(id);
		
		//Trata o nome repetido
		tratarNomeRepetido(categoriaOcorrencia.getNome(), bindingResult);
		
		//Passa os par�metros da categoria para a categoria buscada
		BeanUtils.copyProperties(categoriaOcorrencia, categoriaOcorrenciaBuscada, "id");
		
		//Altera a categoria no banco de dados
		categoriaOcorrenciaDAO.alterar(categoriaOcorrenciaBuscada);
		
		return categoriaOcorrencia;
	}
	
	/**
	 * Busca uma determinada categoria atrav�s do seu ID
	 * @param id
	 * @return
	 * @throws EntidadeNaoEncontradaException
	 */
	public CategoriaOcorrencia buscarPorId(Long id) throws EntidadeNaoEncontradaException {
		CategoriaOcorrencia categoriaOcorrencia = categoriaOcorrenciaDAO.buscar(id);
		
		//Verifica se a categoria existe
		if(categoriaOcorrencia == null){
			throw new EntidadeNaoEncontradaException();
		}
		
		return categoriaOcorrencia;
	}
	
	/**
	 * Busca todas as categorias de ocorr�ncia cadastrada no banco de dados
	 * @return
	 */
	public List<CategoriaOcorrencia> buscarTodos(){
		return categoriaOcorrenciaDAO.buscarTodos();
	}
	

	/**
	 * Realiza o cadastro da categoria
	 * @param categoriaOcorrencia - A categoria que ser� cadastrada
	 * @param bindingResult - Objeto com poss�veis erros de valida��o
	 * @return
	 * @throws ValidacaoException
	 */
	public CategoriaOcorrencia cadastrar(CategoriaOcorrencia categoriaOcorrencia, BindingResult bindingResult) throws ValidacaoException {
		
		//Verifica os poss�veis erros nos campos da categoria de ocorr�ncia
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Verifica se o nome da categoria de ocorr�ncia j� esta sendo utilizada
		tratarNomeRepetido(categoriaOcorrencia.getNome(), bindingResult);
		
		//Realiza o cadastro
		categoriaOcorrenciaDAO.inserir(categoriaOcorrencia);
		
		return categoriaOcorrencia;	
	}

	/**
	 * Deleta uma categoria do banco de dados
	 * @param id
	 * @throws EntidadeNaoEncontradaException - Caso a categoria a ser deletada n�o exista
	 */
	public void deletar(Long id) throws EntidadeNaoEncontradaException {
		categoriaOcorrenciaDAO.deletar(buscarPorId(id));
	}

}
