package com.example.user.config.paging;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PagingParam {
    String path() default "";
}
