package br.com.aroma.aroma_delivery.anotacao.cartao.cvv;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CvvValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CvvValido {

  String message() default "CVV inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
