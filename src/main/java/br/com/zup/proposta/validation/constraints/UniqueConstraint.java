package br.com.zup.proposta.validation.constraints;


import br.com.zup.proposta.validation.annotation.Unique;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueConstraint implements ConstraintValidator<Unique, Object> {

    private EntityManager manager;

    private String field;

    private String alias;

    private Class<?> modelClass;

    public UniqueConstraint(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        field = constraintAnnotation.field();
        var alias = constraintAnnotation.alias();
        this.alias = !alias.isEmpty() ? alias : field;
        modelClass = constraintAnnotation.modelClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;
        return createQuery().setParameter("value", value).getSingleResult() == 0;
    }

    private String getTableName() {
        return modelClass.getSimpleName();
    }

    private TypedQuery<Long> createQuery() {
        return manager.createQuery("select count(t) from " + getTableName() + " t where t." + alias + " = :value", Long.class);
    }
}
