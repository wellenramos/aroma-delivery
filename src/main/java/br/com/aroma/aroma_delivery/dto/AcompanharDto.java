package br.com.aroma.aroma_delivery.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AcompanharDto {

  private Long id;
  private BigDecimal valorTotal;
  private List<ItemDto> itens;
  private List<StatusEtapaDto> etapas;

  @Getter
  @Setter
  @Builder
  public static class ItemDto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
  }

  @Data
  @Builder
  public static class StatusEtapaDto {
    private String etapa;
    private boolean completo;
    private String hora;
  }
}
