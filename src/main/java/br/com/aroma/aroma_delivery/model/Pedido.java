package br.com.aroma.aroma_delivery.model;

import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "pedido")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_id")
  @SequenceGenerator(name = "pedido_id", sequenceName = "pedido_seq", allocationSize = 1)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @ManyToOne
  @JoinColumn(name = "endereco_id")
  private Endereco endereco;

  @Column(name = "data_solicitacao")
  private LocalDate dataSolicitacao;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private StatusPedidoEnum status;

  @Column(name = "valor_total")
  private BigDecimal valorTotal;

  @Column(name = "nota_avaliacao")
  private Integer notaAvaliacao;

  @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
  private Pagamento pagamento;

  @OneToMany(mappedBy = "pedido")
  private List<ItemCarrinho> itens = new ArrayList<>();
}