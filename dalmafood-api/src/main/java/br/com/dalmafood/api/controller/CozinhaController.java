package br.com.dalmafood.api.controller;

import br.com.dalmafood.api.domain.model.Cozinha;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import br.com.dalmafood.api.util.exceptions.parametros.ParametroNuloException;
import br.com.dalmafood.api.util.service.cadastro.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas/")
public class CozinhaController {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Cozinha> cozinhas = cadastroCozinha.todos();
		return ResponseEntity.ok(cozinhas);
	}

	@GetMapping("{idCozinha}")
	public ResponseEntity<?> buscar(@PathVariable Long idCozinha) {
		try {
			return ResponseEntity.ok(cadastroCozinha.buscar(idCozinha));
		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNulaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cozinha cozinha) {
		try {
			cadastroCozinha.salvar(cozinha);
			return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
		} catch (ParametroNuloException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
	
	@PutMapping("{idCozinha}")
	public ResponseEntity<?> atualizar(@PathVariable Long idCozinha, @RequestBody Cozinha cozinha) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(cadastroCozinha.atualizar(idCozinha, cozinha));

		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("{idCozinha}")
	public ResponseEntity<?> remover(@PathVariable Long idCozinha) {
		try {
			cadastroCozinha.remover(idCozinha);
			return ResponseEntity.noContent().build();

		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} catch (EntidadeNulaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}