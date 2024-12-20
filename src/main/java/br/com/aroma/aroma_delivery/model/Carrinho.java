package br.com.aroma.aroma_delivery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carrinho_id_gen")
    @SequenceGenerator(name = "carrinho_id_gen", sequenceName = "carrinho_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens;

    public BigDecimal valorFrete() {
        return BigDecimal.valueOf(5);
    }

    public BigDecimal calcularSubtotalPedido() {
        return itens.stream()
            .map(ItemCarrinho::calcularValorTotalItem)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculartotalPedido(BigDecimal subTotal) {
        return subTotal.add(valorFrete());
    }
}
