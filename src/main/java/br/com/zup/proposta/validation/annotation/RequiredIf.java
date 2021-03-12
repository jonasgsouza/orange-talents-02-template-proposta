package br.com.zup.proposta.validation.annotation;

import br.com.zup.proposta.validation.constraints.RequiredIfConstraint;
import br.com.zup.proposta.validation.interfaces.RequiredIfSupplier;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RequiredIfConstraint.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredIf {

    Class<? extends RequiredIfSupplier> condition();

    String field();

    String message() default "obrigatório";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
