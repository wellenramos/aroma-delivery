package br.com.aroma.aroma_delivery.anotacao.cartao.ano;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AnoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoValido {

  String message() default "Ano de expiração inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
