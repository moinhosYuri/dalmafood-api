package br.com.dalmafood.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dalmafood.api.domain.model.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
