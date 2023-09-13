package br.com.dalmafood.api.util.service.cadastro;

import br.com.dalmafood.api.domain.model.Estado;
import br.com.dalmafood.api.domain.repository.EstadoRepository;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNaoInformadaException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNulaException;
import br.com.dalmafood.api.util.exceptions.identificador.IdentificadorInvalidoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

@Service
public class CadastroEstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) throws EntidadeNaoInformadaException {
        if (Objects.isNull(estado)) {
            throw new EntidadeNaoInformadaException("Valor de estado não adicionado(ao adicionar)!");
        }
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Long idEstado, Estado estado)
            throws IdentificadorInvalidoException, EntidadeNulaException, EntidadeNaoInformadaException {
        if (Objects.isNull(idEstado)) {
            throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
                    Estado.class.getName()));
        }

        Estado estadoAtual = estadoRepository.findById(idEstado)
                .orElseThrow(IdentificadorInvalidoException::new);

        if (Objects.isNull(estadoAtual)) {
           throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui atributos obrigatórios" +
                   " NULOS!", Estado.class.getName()));
        }

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return salvar(estadoAtual);
    }

    public void remover(Long idEstado)
            throws IdentificadorInvalidoException, EntidadeNulaException,
            EntidadeEmUsoException, IllegalArgumentException {

            if (Objects.isNull(idEstado)) {
                throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
                        Estado.class.getName()));
            }

        Estado estadoRemove = estadoRepository.findById(idEstado)
                .orElseThrow(IdentificadorInvalidoException::new);

        try {
            estadoRepository.delete(estadoRemove);
        } catch (IllegalArgumentException e) {
            throw new EntidadeNulaException();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException();
        }
    }

    public List<Estado> todos() {
        return estadoRepository.findAll();
    }

    public Estado buscar(Long idEstado) throws IdentificadorInvalidoException, EntidadeNulaException {
        if (Objects.isNull(idEstado)) {
            throw new IdentificadorInvalidoException(String.format("Valor de identificador " +
                            "(%d) de %s NULO!", idEstado, Estado.class.getName()));
        }

        Estado estadoBusca = estadoRepository.findById(idEstado).orElseThrow(IdentificadorInvalidoException::new);

        if (Objects.isNull(estadoBusca)) {
            throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui " +
                    "atributos obrigatórios NULOS!", Estado.class.getName()));
        }

        return estadoBusca;
    }

}
