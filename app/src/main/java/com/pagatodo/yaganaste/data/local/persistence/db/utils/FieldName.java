package com.pagatodo.yaganaste.data.local.persistence.db.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jguerras on 09/01/2017.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {
    String value();

    boolean primaryKey() default false;

    boolean autoIncrement() default false;
}
