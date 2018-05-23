package br.senai.sp.info.pweb.jucacontrol.ws.rest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.info.pweb.jucacontrol.exceptions.EntidadeNaoEncontradaException;
import br.senai.sp.info.pweb.jucacontrol.exceptions.ValidacaoException;
import br.senai.sp.info.pweb.jucacontrol.models.CategoriaOcorrencia;
import br.senai.sp.info.pweb.jucacontrol.services.CategoriaOcorrenciaService;
import br.senai.sp.info.pweb.jucacontrol.utils.MapUtils;

@RestController
@RequestMapping("/rest/categorias")
public class CategoriaOcorrenciaRestController {
	
	@Autowired
	private CategoriaOcorrenciaService categoriaOcorrenciaService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPorId(@PathVariable Long id){
		
		try {
			return ResponseEntity
						.ok(categoriaOcorrenciaService.buscarPorId(id));
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.header("X-Reason", "Entidade não encontrada")
						.build();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
					
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> buscarTodos(){
		
		
		try {
			
			return ResponseEntity
					.ok(categoriaOcorrenciaService.buscarTodos());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
			
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody CategoriaOcorrencia categoriaOcorrencia, BindingResult bindingResult){
		try {
			return 
					ResponseEntity
						.ok(categoriaOcorrenciaService.cadastrar(categoriaOcorrencia, bindingResult));
		} catch (ValidacaoException e) {
			
			return
					ResponseEntity
						.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body(MapUtils.mapaDe(bindingResult));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return
					ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> alterar(@PathVariable Long id, @Valid @RequestBody CategoriaOcorrencia categoriaOcorrencia, BindingResult bindingResult){
		
		try {
			return
					ResponseEntity
						.ok(categoriaOcorrenciaService.alterar(id, categoriaOcorrencia, bindingResult));
			
		} catch (ValidacaoException e) {
			
			return
					ResponseEntity
						.status(HttpStatus.UNPROCESSABLE_ENTITY)
						.body(MapUtils.mapaDe(bindingResult));
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return
					ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.header("X-Reason", "Entidade não encontrada")
						.build();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return
					ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id){
		
		try {
			categoriaOcorrenciaService.deletar(id);
			
			return 
					ResponseEntity
						.noContent()
						.build();
		} catch (EntidadeNaoEncontradaException e) {
			
			return
					ResponseEntity
						.status(HttpStatus.NOT_FOUND)
						.header("X-Reason", "Entidade não encontrada")
						.build();
		} catch (Exception e) {
			
			return
					ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.build();
		}
	}

}
