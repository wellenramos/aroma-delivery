package br.com.aroma.aroma_delivery.model;

import br.com.aroma.aroma_delivery.dto.enums.TamanhoEnum;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "item_carrinho")
public class ItemCarrinho {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_carrinho_id_gen")
  @SequenceGenerator(name = "item_carrinho_id_gen", sequenceName = "item_carrinho_seq", allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "produto_id")
  private Produto produto;

  @ManyToOne
  @JoinColumn(name = "carrinho_id")
  private Carrinho carrinho;

  @Column(name = "quantidade")
  private Integer quantidade;

  @Column(name = "observacao")
  private String observacao;

  @Enumerated(EnumType.STRING)
  @Column(name = "tamanho_copo")
  private TamanhoEnum tamanhoCopo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  public void addCarrinho(Carrinho carrinho) {
    if (carrinho.getItens() == null) {
      carrinho.setItens(new ArrayList<>());
    }
    if (!carrinho.getItens().contains(this)) {
      carrinho.getItens().add(this);
    }
    this.carrinho = carrinho;
  }
}
