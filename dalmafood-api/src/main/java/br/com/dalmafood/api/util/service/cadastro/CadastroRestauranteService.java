package br.com.dalmafood.api.util.service.cadastro;

import br.com.dalmafood.api.domain.model.Restaurante;
import br.com.dalmafood.api.domain.repository.RestauranteRepository;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNaoInformadaException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CadastroRestauranteService {

	@Autowired
	RestauranteRepository restauranteRepository;

	public void remover(Long idRestaurante)
			throws IdentificadorInvalidoException, EntidadeEmUsoException, EntidadeNulaException {
		if (Objects.isNull(idRestaurante)) {
			throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
					this.getClass().getName()));
		}

		Restaurante restauranteRemove = restauranteRepository.findById(idRestaurante)
				.orElseThrow(() -> new IdentificadorInvalidoException(String.format("Id %d da classe %s é inválido",
						idRestaurante, this.getClass().getName())));
		try {
			restauranteRepository.delete(restauranteRemove);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNulaException();

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A entidade %s de id %d está em uso",
					this.getClass().getName(), idRestaurante));
		}

	}

	public Restaurante salvar(Restaurante restaurante) throws EntidadeNaoInformadaException {
		if (restaurante != null) {
			return restauranteRepository.save(restaurante);
		}
		throw new EntidadeNaoInformadaException("Algum valor dos parâmetros do objeto \"Cozinha\" " +
				"foi dado como NULO em seu POST!");
	}

	public Restaurante atualizar(Long idRestaurante, Restaurante restaurante)
			throws IdentificadorInvalidoException, EntidadeNulaException, EntidadeNaoInformadaException {
		if (Objects.isNull(idRestaurante)) {
			throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
					Restaurante.class.getName()));
		}

		if (Objects.isNull(restaurante) ||
				Objects.isNull(restaurante.getNome()) ||
				Objects.isNull(restaurante.getTaxaFrete())) {
			throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui atributos obrigatórios" +
					" NULOS!", Restaurante.class.getName()));
		}

		Restaurante restauranteAtual = restauranteRepository.findById(idRestaurante)
				.orElseThrow(IdentificadorInvalidoException::new);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "produtos", "endereco");

		return salvar(restauranteAtual);
	}

	public Restaurante buscar(Long idRestaurante) throws IdentificadorInvalidoException, EntidadeNulaException {
		if (Objects.isNull(idRestaurante)) {
			throw new IdentificadorInvalidoException(String.format("Valor de identificador " +
					"(%d) de %s NULO!", idRestaurante, Restaurante.class.getName()));
		}

		Restaurante restauranteBusca = restauranteRepository
				.findById(idRestaurante).orElseThrow(IdentificadorInvalidoException::new);

		if (Objects.isNull(restauranteBusca)) {
			throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui " +
					"atributos obrigatórios NULOS!", Restaurante.class.getName()));
		}

		return restauranteBusca;
	}

	public List<Restaurante> todos() {
		return restauranteRepository.findAll();
	}

}
