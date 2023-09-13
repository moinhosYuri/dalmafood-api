package br.com.dalmafood.api.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Cozinha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;


	@JsonIgnore
	@OneToMany(targetEntity = Restaurante.class)
	@JoinColumn(name = "restaurante_id")
	List<Restaurante> restaurantes;

}
