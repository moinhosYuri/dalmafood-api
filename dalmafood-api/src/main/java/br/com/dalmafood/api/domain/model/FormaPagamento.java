package br.com.dalmafood.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FormaPagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String descricao;

	// TODO Implementar forma de pagamento com relação a Restaurante
	 @ManyToMany(targetEntity = Restaurante.class)
	 private Set<Restaurante> restaurantes;

}
