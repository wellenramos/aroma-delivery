package br.com.aroma.aroma_delivery.dto;

import br.com.aroma.aroma_delivery.dto.CarrinhoResumoDto.ItemCarrinhoResumoDto;
import java.math.BigDecimal;
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
public class CarrinhoResumoItemDto {

  private BigDecimal subTotal;
  private BigDecimal valorTotal;
  private ItemCarrinhoResumoDto item;
}
