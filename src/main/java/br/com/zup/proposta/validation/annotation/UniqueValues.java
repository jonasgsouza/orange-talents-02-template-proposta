package br.com.zup.proposta.validation.annotation;

import br.com.zup.proposta.validation.constraints.UniqueValuesConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueValuesConstraint.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueValues {

    String[] fields();

    Class<?> modelClass();

    String message() default "deve ser único";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
