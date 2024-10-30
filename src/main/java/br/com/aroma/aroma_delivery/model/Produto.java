package br.com.aroma.aroma_delivery.model;

import br.com.aroma.aroma_delivery.dto.SituacaoProdutoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_gen")
    @SequenceGenerator(name = "produto_id_gen", sequenceName = "produto_seq", allocationSize = 1)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private SituacaoProdutoEnum situacao;

    @OneToMany(mappedBy = "produto")
    private List<ItemAdicional> adicionais;
}
