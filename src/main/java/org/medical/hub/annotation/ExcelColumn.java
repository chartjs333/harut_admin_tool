package org.medical.hub.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ExcelColumn {
    String name();

    boolean required() default false;

    String[] map_from() default {};

    String[] map_to() default {};

    String value() default "";
}
