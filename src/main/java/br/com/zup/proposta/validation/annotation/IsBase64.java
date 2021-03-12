package br.com.zup.proposta.validation.annotation;

import br.com.zup.proposta.validation.constraints.IsBase64Constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsBase64Constraint.class)
public @interface IsBase64 {

    String message() default "{messages.Base64}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
