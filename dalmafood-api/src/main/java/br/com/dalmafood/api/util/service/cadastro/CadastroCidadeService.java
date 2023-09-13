package br.com.dalmafood.api.util.service.cadastro;

import br.com.dalmafood.api.domain.model.Cidade;
import br.com.dalmafood.api.domain.repository.CidadeRepository;
import br.com.dalmafood.api.domain.repository.EstadoRepository;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeEmUsoException;
import br.com.dalmafood.api.util.exceptions.entidades.EntidadeNaoInformadaException;
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
public class CadastroCidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;


    public Cidade salvar(Cidade cidade)
            throws EntidadeNaoInformadaException, EntidadeNulaException, IdentificadorInvalidoException {
        if (Objects.isNull(cidade)) {
            throw new EntidadeNaoInformadaException("Valor de cidade não adicionado(ao adicionar)!");
        }

        if (Objects.isNull(cidade.getEstado())) {
            throw new EntidadeNulaException("Cidade possui estado NULO!");
        }

        Long idCidade = cidade.getEstado().getId();
        cidade.setEstado(estadoRepository.findById(idCidade)
                .orElseThrow(IdentificadorInvalidoException::new));

        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar(Long idCidade, Cidade cidade)
            throws IdentificadorInvalidoException, EntidadeNulaException, EntidadeNaoInformadaException {
        if (Objects.isNull(idCidade)) {
            throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
                    Cidade.class.getName()));
        }

        if (Objects.isNull(cidade) || Objects.isNull(cidade.getNome())) {
           throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui atributos obrigatórios" +
                   " NULOS!", Cidade.class.getName()));
        }

        Cidade cidadeAtual = cidadeRepository.findById(idCidade)
                .orElseThrow(IdentificadorInvalidoException::new);
        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        return salvar(cidadeAtual);
    }

    public void remover(Long idCidade)
            throws IdentificadorInvalidoException, EntidadeNulaException, EntidadeEmUsoException {

            if (Objects.isNull(idCidade)) {
                throw new IdentificadorInvalidoException(String.format("Valor de identificador de %s NULO!",
                        Cidade.class.getName()));
            }

        Cidade cidadeRemove = cidadeRepository.findById(idCidade)
                .orElseThrow(IdentificadorInvalidoException::new);

        if (Objects.isNull(cidadeRemove.getNome())) {
            throw new ParametroNuloException(String.format("A entidade %s está NULA ou possui atributos obrigatórios" +
                    " NULOS!", Cidade.class.getName()));
        }

        try {
            cidadeRepository.delete(cidadeRemove);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNulaException();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException();
        }
    }

    public List<Cidade> todos() {
        return cidadeRepository.findAll();
    }

    public Cidade buscar(Long idCidade)
            throws IdentificadorInvalidoException, EntidadeNulaException {
        if (Objects.isNull(idCidade)) {
            throw new IdentificadorInvalidoException(String.format("Valor de identificador " +
                    "(%d) de %s NULO!", idCidade, Cidade.class.getName()));
        }

        Cidade cidadeBusca = cidadeRepository.findById(idCidade)
                .orElseThrow(IdentificadorInvalidoException::new);;

        if (Objects.isNull(cidadeBusca)) {
            throw new EntidadeNulaException(String.format("A entidade %s está NULA ou possui " +
                    "atributos obrigatórios NULOS!", Cidade.class.getName()));
        }

        return cidadeBusca;
    }

}
