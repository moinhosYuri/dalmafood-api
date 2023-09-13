package br.com.dalmafood.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

//To use the @Data annotation you should add the Lombok dependency.
@Data
@Embeddable
public class Endereco {

    @Column(name = "endereco_cep")
    private String cep;

    @Column(name = "endereco_logradouro")
    private String logradouro;

    @Column(name = "endereco_complemento")
    private String complemento;

    @Column(name = "endereco_numero")
    private String numero;

    @Column(name = "endereco_bairro")
    private String bairro;

    @JoinColumn(name = "data_cadastro")
    @CreationTimestamp
    LocalDateTime dataCadastro;

    @JoinColumn(name = "data_atualizacao")
    @UpdateTimestamp
    LocalDateTime dataAtualizacao;


    @ManyToOne(targetEntity = Cidade.class)
    private Cidade cidade;

}