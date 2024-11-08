package br.com.aroma.aroma_delivery.anotacao.cartao.mes;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MesValido {

  String message() default "Mês de expiração inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
