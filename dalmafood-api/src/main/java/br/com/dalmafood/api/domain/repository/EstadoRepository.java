package br.com.dalmafood.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dalmafood.api.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
