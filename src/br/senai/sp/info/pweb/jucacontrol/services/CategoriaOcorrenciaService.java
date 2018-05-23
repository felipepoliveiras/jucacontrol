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
	 * Dispara uma {@link ValidacaoException} caso o nome da categoria já esteja sendo utilizada
	 * @param nome
	 * @param bindingResult
	 * @throws ValidacaoException
	 */
	public void tratarNomeRepetido(String nome, BindingResult bindingResult) throws ValidacaoException {
		
		//Realiza a busca pelo nome
		if(!categoriaOcorrenciaDAO.buscarPorCampo("nome", nome).isEmpty()) {
			bindingResult.addError(new FieldError("categoriaOcorrencia", "nome", "O nome '" + nome + "' já está sendo utilizado"));
			throw new ValidacaoException();
		}
		
	}
	
	/**
	 * Realiza a alteração de uma categoria de ocorrência
	 * @param id - O id da categoria de ocorrência que será alterada
	 * @param categoriaOcorrencia - Objeto com os novos dados da categoria de ocorrência
	 * @param bindingResult - Objeto com erros de validação
	 * @return
	 * @throws ValidacaoException - Caso a cateogira possua erros de validação
	 * @throws EntidadeNaoEncontradaException  - Caso a categoria alterada não exista
	 */
	public CategoriaOcorrencia alterar(Long id, CategoriaOcorrencia categoriaOcorrencia, BindingResult bindingResult) throws ValidacaoException, EntidadeNaoEncontradaException {
		
		//Verifica se a categoria possui erros de validação
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Realiza a busca da categoria através do seu ID
		CategoriaOcorrencia categoriaOcorrenciaBuscada = buscarPorId(id);
		
		//Trata o nome repetido
		tratarNomeRepetido(categoriaOcorrencia.getNome(), bindingResult);
		
		//Passa os parâmetros da categoria para a categoria buscada
		BeanUtils.copyProperties(categoriaOcorrencia, categoriaOcorrenciaBuscada, "id");
		
		//Altera a categoria no banco de dados
		categoriaOcorrenciaDAO.alterar(categoriaOcorrenciaBuscada);
		
		return categoriaOcorrencia;
	}
	
	/**
	 * Busca uma determinada categoria através do seu ID
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
	 * Busca todas as categorias de ocorrência cadastrada no banco de dados
	 * @return
	 */
	public List<CategoriaOcorrencia> buscarTodos(){
		return categoriaOcorrenciaDAO.buscarTodos();
	}
	

	/**
	 * Realiza o cadastro da categoria
	 * @param categoriaOcorrencia - A categoria que será cadastrada
	 * @param bindingResult - Objeto com possíveis erros de validação
	 * @return
	 * @throws ValidacaoException
	 */
	public CategoriaOcorrencia cadastrar(CategoriaOcorrencia categoriaOcorrencia, BindingResult bindingResult) throws ValidacaoException {
		
		//Verifica os possíveis erros nos campos da categoria de ocorrência
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException();
		}
		
		//Verifica se o nome da categoria de ocorrência já esta sendo utilizada
		tratarNomeRepetido(categoriaOcorrencia.getNome(), bindingResult);
		
		//Realiza o cadastro
		categoriaOcorrenciaDAO.inserir(categoriaOcorrencia);
		
		return categoriaOcorrencia;	
	}

	/**
	 * Deleta uma categoria do banco de dados
	 * @param id
	 * @throws EntidadeNaoEncontradaException - Caso a categoria a ser deletada não exista
	 */
	public void deletar(Long id) throws EntidadeNaoEncontradaException {
		categoriaOcorrenciaDAO.deletar(buscarPorId(id));
	}

}
