package com.mariolemos.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mariolemos.cursomc.domain.Produto;
import com.mariolemos.cursomc.dto.ProdutoDTO;
import com.mariolemos.cursomc.resources.utils.URL;
import com.mariolemos.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;	

	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	 public ResponseEntity<Page<ProdutoDTO>> findPage(
			 @RequestParam(value="nome", defaultValue = "") String nome,
			 @RequestParam(value="categorias", defaultValue = "") String categorias,
			 @RequestParam(value="page", defaultValue = "0") Integer page,
			 @RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage,
			 @RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			 @RequestParam(value="direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);	 
		Page<Produto> lista = service.search(nomeDecoded, ids, page,
				 linesPerPage, orderBy, direction);
		 Page<ProdutoDTO> listaDto = lista.map(obj -> new ProdutoDTO(obj));
		 return ResponseEntity.ok().body(listaDto);
	}

	/*@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	 public ResponseEntity<Void> salvar(@RequestBody Produto obj) {
		 service.salvar(obj);
		 return ResponseEntity.ok().build();
	 }
	@RequestMapping(method = RequestMethod.GET)
	 public ResponseEntity<List<Produto>> listarTodos() {
		 List<Produto> obj = service.buscarTodos();
		 return ResponseEntity.ok().body(obj);
	 }*/

}
