package com.github.apycazo.hi5.shared.annotations;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Andres Picazo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@JsonInclude(JsonInclude.Include.NON_NULL)
public @interface JsonSerializable {
}
