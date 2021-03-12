package br.com.zup.proposta.validation.annotation;

import br.com.zup.proposta.validation.constraints.ExistsConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ExistsConstraint.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Exists {
    String field();

    String alias() default "";

    Class<?> modelClass();

    String message() default "{messages.Exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
