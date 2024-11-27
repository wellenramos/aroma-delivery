package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.dto.enums.StatusPedidoEnum;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PedidoAdminDetalheDto {

  private Long id;
  private String cliente;
  private String endereco;
  private BigDecimal total;
  private StatusPedidoEnum status;
  private List<ItemPedidoAdminDto> itens;

  @Getter
  @Setter
  @Builder
  public static class ItemPedidoAdminDto {
    private String produto;
    private Integer quantidade;
    private BigDecimal preco;
  }
}