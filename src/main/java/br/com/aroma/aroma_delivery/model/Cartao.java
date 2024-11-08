package br.com.aroma.aroma_delivery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cartao")
public class Cartao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartao_id")
  @SequenceGenerator(name = "cartao_id", sequenceName = "cartao_seq", allocationSize = 1)
  private Long id;

  @Column(name = "numero")
  private String numero;

  @Column(name = "titular")
  private String titular;

  @Column(name = "validade_mes")
  private Integer validadeMes;

  @Column(name = "validade_ano")
  private Integer validadeAno;

  @Column(name = "cvv")
  private String cvv;

  @Column(name = "bandeira")
  private String bandeira;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

}