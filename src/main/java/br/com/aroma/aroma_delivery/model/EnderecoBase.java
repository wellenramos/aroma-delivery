package br.com.aroma.aroma_delivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "endereco_base")
public class EnderecoBase {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_base_id")
  @SequenceGenerator(name = "endereco_base_id", sequenceName = "endereco_base_seq", allocationSize = 1)
  private Long id;

  @Column(name = "cep", nullable = false)
  private String cep;

  @Column(name = "bairro")
  private String bairro;

  @Column(name = "cidade")
  private String cidade;

  @Column(name = "estado")
  private String estado;

}