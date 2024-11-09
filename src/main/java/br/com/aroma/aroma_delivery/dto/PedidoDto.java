package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import br.com.aroma.aroma_delivery.model.Endereco;
import br.com.aroma.aroma_delivery.model.ItemCarrinho;
import br.com.aroma.aroma_delivery.model.Pagamento;
import br.com.aroma.aroma_delivery.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDto {

  private Long id;

  @JsonIgnore
  private Usuario usuario;

  @JsonIgnore
  private Endereco endereco;

  private LocalDate dataSolicitacao;
  private StatusPedidoEnum status;
  private BigDecimal valorTotal;
  private Boolean favorito;

  @JsonIgnore
  private Pagamento pagamento;

  @JsonIgnore
  private List<ItemCarrinho> itens = new ArrayList<>();
}
