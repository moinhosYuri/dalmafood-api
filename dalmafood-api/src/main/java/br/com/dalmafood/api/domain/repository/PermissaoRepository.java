package br.com.dalmafood.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dalmafood.api.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
