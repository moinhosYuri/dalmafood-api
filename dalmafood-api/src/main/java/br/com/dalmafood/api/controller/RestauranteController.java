package br.com.dalmafood.api.controller;

import br.com.dalmafood.api.domain.model.Restaurante;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNaoInformadaException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import br.com.dalmafood.api.util.service.cadastro.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.ok(cadastroRestaurante.todos());
	}

	@GetMapping("{idRestaurante}")
	public ResponseEntity<?> buscar(@PathVariable Long idRestaurante) {
		try {
			Restaurante restaurante = cadastroRestaurante.buscar(idRestaurante);
			return ResponseEntity.ok(restaurante);
		} catch (EntidadeNulaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
		try {
			cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoInformadaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{idRestaurante}")
	public ResponseEntity<?> atualizar(@PathVariable Long idRestaurante, @RequestBody Restaurante restaurante) {
		try {
			return ResponseEntity.ok(cadastroRestaurante.atualizar(idRestaurante, restaurante));

		} catch (EntidadeNulaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (EntidadeNaoInformadaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}

	@DeleteMapping("{idRestaurante}")
	public ResponseEntity<?> remover(@PathVariable Long idRestaurante) {
		try {
			cadastroRestaurante.remover(idRestaurante);
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