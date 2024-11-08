package br.com.aroma.aroma_delivery.anotacao.cartao.mes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MesValidator implements ConstraintValidator<MesValido, Integer> {

    @Override
    public boolean isValid(Integer mes, ConstraintValidatorContext context) {
        if (mes == null) {
            return false;
        }
        return mes >= 1 && mes <= 12;
    }
}
