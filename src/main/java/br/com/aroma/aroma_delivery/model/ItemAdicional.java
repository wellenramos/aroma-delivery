package br.com.aroma.aroma_delivery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_adicional")
public class ItemAdicional {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_adicional_id_gen")
    @SequenceGenerator(name = "item_adicional_id_gen", sequenceName = "item_adicional_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "adicional_id")
    private Produto adicional;
}