package br.com.aroma.aroma_delivery.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoResumoDto {

  private List<ItemCarrinhoResumoDto> itens;

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ItemCarrinhoResumoDto {
    private Long id;
    private String nomeProduto;
    private String descricaoProduto;
    private BigDecimal valorTotal;
    private Integer quantidade;
  }
}
