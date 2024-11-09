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
@Table(name = "avaliacao")
public class Avaliacao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avaliacao_id")
  @SequenceGenerator(name = "avaliacao_id", sequenceName = "avaliacao_seq", allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  @Column(name = "nota")
  private Integer nota;

  @Column(name = "comentario")
  private String comentario;

}