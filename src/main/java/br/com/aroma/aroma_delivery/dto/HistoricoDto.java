package br.com.aroma.aroma_delivery.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HistoricoDto {

  private Long id;
  private String status;
  private Integer notaAvaliacao;
  private List<ItemHistoricoDto> itens;

  @Getter
  @Setter
  @Builder
  public static class ItemHistoricoDto {
    private Long id;
    private String nome;
    private Integer quantidade;
  }
}
