package br.com.aroma.aroma_delivery.anotacao.cartao.numero;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumeroCartaoValidoValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumeroCartaoValido {

  String message() default "Número de cartão de crédito inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
