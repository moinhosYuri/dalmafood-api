package br.com.dalmafood.api.controller;

import br.com.dalmafood.api.domain.model.Cidade;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNaoInformadaException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import br.com.dalmafood.api.util.service.cadastro.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades/")
public class CidadeController {

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(cadastroCidade.todos());
	}

	@GetMapping("{idCidade}")
	public ResponseEntity<?> buscar(@PathVariable Long idCidade) {
		try {
			Cidade cidadeUnitario = cadastroCidade.buscar(idCidade);
			return ResponseEntity.ok().body(cidadeUnitario);
		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNulaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
		try {
			cadastroCidade.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNulaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNaoInformadaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{idCidade}")
	public ResponseEntity<?> atualizar(@PathVariable Long idCidade, @RequestBody Cidade cidade) {
		try {
			return ResponseEntity.ok(cadastroCidade.atualizar(idCidade, cidade));

		} catch (EntidadeNulaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());

		} catch (IdentificadorInvalidoException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeNaoInformadaException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


	@DeleteMapping("/{idCidade}")
	public ResponseEntity<?> remover(@PathVariable Long idCidade) {
		try {
			cadastroCidade.remover(idCidade);
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
