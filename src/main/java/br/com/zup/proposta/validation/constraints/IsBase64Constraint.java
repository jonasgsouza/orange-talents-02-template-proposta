package br.com.zup.proposta.validation.constraints;

import br.com.zup.proposta.validation.annotation.IsBase64;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsBase64Constraint implements ConstraintValidator<IsBase64, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Base64.isBase64(s);
    }
}
