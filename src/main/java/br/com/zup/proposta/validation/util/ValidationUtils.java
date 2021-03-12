package br.com.zup.proposta.validation.util;

import br.com.zup.proposta.validation.annotation.FieldAlias;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

public class ValidationUtils {

    public static String getFieldAlias(@NotNull Object o, @NotNull String fieldName, String defaultValue) {
        try {
            Assert.notNull(o, "Deve ser informado o objeto");
            Assert.notNull(fieldName, "Deve ser informado o nome do atributo");
            var fieldAlias = o.getClass().getDeclaredField(fieldName).getAnnotation(FieldAlias.class);
            return fieldAlias == null ? defaultValue : fieldAlias.value();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getTableName(@NotNull Class<?> modelClass) {
        Assert.notNull(modelClass, "Deve ser informada a classe");
        return modelClass.getSimpleName();
    }

}
