package br.com.aroma.aroma_delivery.model;

import br.com.aroma.aroma_delivery.dto.enums.StatusPagamentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamento")
public class Pagamento {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartao_id")
  @SequenceGenerator(name = "cartao_id", sequenceName = "pagamento_seq", allocationSize = 1)
  private Long id;

  @OneToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cartao_id")
  private Cartao cartao;

  @Column(name = "valor")
  private BigDecimal valor;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusPagamentoEnum status;

  @Column(name = "data_pagamento")
  private LocalDate dataPagamento;

  @Column(name = "metodo_pagamento")
  private String metodoPagamento;
}