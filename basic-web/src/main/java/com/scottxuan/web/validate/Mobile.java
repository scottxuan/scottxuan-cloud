package com.scottxuan.web.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author : scottxuan
 */

@Constraint(validatedBy = MobileValidate.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mobile {
    String message() default "error.mobile.roles";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
