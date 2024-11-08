package br.com.aroma.aroma_delivery.anotacao.cartao.cvv;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CvvValidator implements ConstraintValidator<CvvValido, String> {

  @Override
  public boolean isValid(String cvv, ConstraintValidatorContext context) {
    if (cvv == null || cvv.isEmpty()) {
      return false;
    }
    return cvv.matches("\\d{3,4}");
  }
}
