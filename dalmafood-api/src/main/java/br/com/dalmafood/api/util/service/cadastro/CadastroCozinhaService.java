package br.com.dalmafood.api.util.service.cadastro;

import br.com.dalmafood.api.domain.model.Cozinha;
import br.com.dalmafood.api.domain.model.Restaurante;
import br.com.dalmafood.api.domain.repository.CozinhaRepository;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import br.com.dalmafood.api.util.exceptions.parametros.ParametroNuloException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		if (cozinha.getNome().isBlank()) {
			throw new ParametroNuloException("Entidade com parâmetro NULO!");
		}

		return cozinhaRepository.save(cozinha);
	}

	public void remover(Long idCozinha)
			throws IdentificadorInvalidoException, EntidadeNulaException, EntidadeEmUsoException {
		if (Objects.isNull(idCozinha)) {
			throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
					Restaurante.class.getName()));
		}

		Cozinha cozinhaRemove = buscar(idCozinha);

		try {
			cozinhaRepository.delete(cozinhaRemove);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNulaException(String.format("Entidade não existe. Classe: %s",
					Cozinha.class.getName()));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Id %d em uso. Classe: %s", idCozinha,
					Cozinha.class.getName()));
		}
	}

	public Cozinha atualizar(Long idCozinha, Cozinha cozinha) throws IdentificadorInvalidoException {
		Cozinha cozinhaAtualizar = cozinhaRepository.findById(idCozinha)
				.orElseThrow(IdentificadorInvalidoException::new);

		BeanUtils.copyProperties(cozinha, cozinhaAtualizar, "id");

		return cozinhaRepository.save(cozinhaAtualizar);
	}

	public Cozinha buscar(Long idCozinha) throws IdentificadorInvalidoException, EntidadeNulaException {
		if (Objects.isNull(idCozinha)) {
			throw new IdentificadorInvalidoException(String.format("Valor de identificador " +
					"(%d) de %s NULO!", idCozinha, Cozinha.class.getName()));
		}

		Cozinha cozinhaBusca = cozinhaRepository.findById(idCozinha)
				.orElseThrow(IdentificadorInvalidoException::new);

		if (Objects.isNull(cozinhaBusca)) {
			throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui " +
					"atributos obrigatórios NULOS!", Cozinha.class.getName()));
		}

		return cozinhaBusca;
	}

	public List<Cozinha> todos() {
		return cozinhaRepository.findAll();
	}

}
