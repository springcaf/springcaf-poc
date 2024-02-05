package com.springcaf.core.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.springcaf.core.jdbc.model.KeyType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
	
	KeyType keyType() default KeyType.UUID;
	String[] keyColumns();
}