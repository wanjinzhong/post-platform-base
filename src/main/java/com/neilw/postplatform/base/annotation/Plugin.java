package com.neilw.postplatform.base.annotation;

import org.pf4j.Extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
@Extension
public @interface Plugin {
}
