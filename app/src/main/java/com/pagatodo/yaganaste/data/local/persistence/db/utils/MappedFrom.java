package com.pagatodo.yaganaste.data.local.persistence.db.utils;

import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Juan Guerra on 06/03/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedFrom {
    Class<? extends AbstractEntity> value();
}
