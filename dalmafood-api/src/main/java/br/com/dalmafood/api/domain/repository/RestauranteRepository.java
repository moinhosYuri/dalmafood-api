package br.com.dalmafood.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dalmafood.api.domain.model.Restaurante;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
