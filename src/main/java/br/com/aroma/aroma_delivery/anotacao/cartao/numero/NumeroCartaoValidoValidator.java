package br.com.aroma.aroma_delivery.anotacao.cartao.numero;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumeroCartaoValidoValidator implements
    ConstraintValidator<NumeroCartaoValido, String> {

  @Override
  public void initialize(NumeroCartaoValido constraintAnnotation) {
  }

  @Override
  public boolean isValid(String numeroCartao, ConstraintValidatorContext context) {
    if (numeroCartao == null) {
      return false;
    }

    numeroCartao = numeroCartao.replaceAll(" ", "");

    if (!numeroCartao.matches("\\d{13,19}")) {
      return false;
    }

    return validarLuhn(numeroCartao);
  }

  private boolean validarLuhn(String numeroCartao) {
    int soma = 0;
    boolean alternar = false;

    for (int i = numeroCartao.length() - 1; i >= 0; i--) {
      int digito = Character.getNumericValue(numeroCartao.charAt(i));

      if (alternar) {
        digito *= 2;
        if (digito > 9) {
          digito -= 9;
        }
      }

      soma += digito;
      alternar = !alternar;
    }

    return soma % 10 == 0;
  }
}
