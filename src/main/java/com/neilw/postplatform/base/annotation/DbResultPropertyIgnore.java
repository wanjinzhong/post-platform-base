package com.neilw.postplatform.base.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbResultPropertyIgnore {
}
