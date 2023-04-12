package com.neilw.postplatform.base.annotation;

import com.neilw.postplatform.base.enums.ParamType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    ParamType type() default ParamType.TEXT;
    String display() default "";
    int order() default 0;
    boolean required() default true;
    String[] options() default {};
}
