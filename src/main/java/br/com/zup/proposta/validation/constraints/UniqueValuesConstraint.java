package br.com.zup.proposta.validation.constraints;

import br.com.zup.proposta.validation.annotation.UniqueValues;
import br.com.zup.proposta.validation.util.ValidationUtils;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UniqueValuesConstraint implements ConstraintValidator<UniqueValues, Object> {
    private EntityManager manager;

    private List<String> fields;

    private Class<?> modelClass;

    private String message;

    public UniqueValuesConstraint(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(UniqueValues constraintAnnotation) {
        fields = Arrays.asList(constraintAnnotation.fields());
        modelClass = constraintAnnotation.modelClass();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) return true;
        var query = createQuery(o);
        var bean = new BeanWrapperImpl(o);
        fields.forEach(field -> query.setParameter(field, bean.getPropertyValue(field)));
        var isValid = query.getSingleResult() == 0;
        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            for (var field : fields) {
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(field).addConstraintViolation();
            }
        }
        return isValid;
    }

    private String getTableName() {
        return modelClass.getSimpleName();
    }

    private TypedQuery<Long> createQuery(Object o) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("select count(t) from ").append(getTableName()).append(" t where ");
        fields.forEach(field -> {
            var alias = ValidationUtils.getFieldAlias(o, field, field);
            stringBuilder
                    .append(" and t.")
                    .append(alias)
                    .append(" = :").append(field);

        });
        return manager.createQuery(stringBuilder.toString().replaceFirst("and", ""), Long.class);
    }
}
