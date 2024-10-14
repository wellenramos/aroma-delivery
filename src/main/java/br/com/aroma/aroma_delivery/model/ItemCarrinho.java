package br.com.aroma.aroma_delivery.model;

import br.com.aroma.aroma_delivery.dto.TamanhoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

}
