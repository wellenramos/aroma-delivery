package br.com.aroma.aroma_delivery.dto.command;

import br.com.aroma.aroma_delivery.anotacao.cartao.ano.AnoValido;
import br.com.aroma.aroma_delivery.anotacao.cartao.cvv.CvvValido;
import br.com.aroma.aroma_delivery.anotacao.cartao.mes.MesValido;
import br.com.aroma.aroma_delivery.anotacao.cartao.numero.NumeroCartaoValido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalvarCartaoCommand {

  private Long id;

  @NumeroCartaoValido(message = "Número de cartão inválido")
  @NotBlank(message = "Número é obrigatório")
  @Size(message = "Limite do campo numero excedido", max = 20)
  private String numero;

  @NotBlank(message = "Titular é obrigatório")
  @Size(message = "Limite do campo titular excedido", max = 100)
  private String titular;

  @MesValido(message = "Mês de expiração inválido")
  @NotNull(message = "Validade Mês é obrigatório")
  private Integer validadeMes;

  @AnoValido(message = "Ano de expiração inválido")
  @NotNull(message = "Validade Ano é obrigatório")
  private Integer validadeAno;

  @CvvValido(message = "CVV inválido")
  @NotBlank(message = "CVV é obrigatório")
  @Size(message = "Limite do campo cvv excedido", max = 4)
  private String cvv;

  public void setNumero(String numero) {
    if (numero != null && !numero.isEmpty()) {
      this.numero = numero.replaceAll(" ", "");
    }
  }
}
