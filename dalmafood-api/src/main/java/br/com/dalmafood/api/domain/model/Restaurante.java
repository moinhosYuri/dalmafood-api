package br.com.dalmafood.api.domain.model;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(name = "taxa_frete")
	@JoinColumn(nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "cozinha_id")
	private Cozinha cozinha;

	@JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private Set<Produto> produtos;

	@Embedded
	private Endereco endereco;

// TODO Implementar forma de pagamento com relação a Restaurante
	@ManyToMany(targetEntity = FormaPagamento.class)
	private Set<FormaPagamento> formasPagamento;

}
