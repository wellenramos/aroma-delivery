package br.com.aroma.aroma_delivery.anotacao.cartao.ano;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class AnoValidator implements ConstraintValidator<AnoValido, Integer> {

  @Override
  public boolean isValid(Integer ano, ConstraintValidatorContext context) {
    if (ano == null) {
      return false;
    }
    int anoAtual = Year.now().getValue();
    return ano >= anoAtual;
  }
}
