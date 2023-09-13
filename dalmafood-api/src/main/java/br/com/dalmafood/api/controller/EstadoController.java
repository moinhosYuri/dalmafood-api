package br.com.dalmafood.api.controller;

import br.com.dalmafood.api.domain.model.Estado;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNaoInformadaException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import br.com.dalmafood.api.util.service.cadastro.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados/")
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(cadastroEstado.todos());
	}

	@GetMapping("{idEstado}")
	public ResponseEntity<?> buscar(@PathVariable Long idEstado) {
		try {
			Estado estadoCidade = cadastroEstado.buscar(idEstado);
			return ResponseEntity.ok(estadoCidade);
		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNulaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Estado estado) {
		try {
			cadastroEstado.salvar(estado);
			return ResponseEntity.status(HttpStatus.CREATED).body(estado);
		} catch (EntidadeNaoInformadaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{idEstado}")
	public ResponseEntity<?> atualizar(@PathVariable Long idEstado, @RequestBody Estado estado) {
		try {
			return ResponseEntity.ok(cadastroEstado.atualizar(idEstado, estado));

		} catch (EntidadeNulaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNaoInformadaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		}
	}


	@DeleteMapping("{idEstado}")
	public ResponseEntity<?> remover(@PathVariable Long idEstado) {
		try {
			cadastroEstado.remover(idEstado);
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
