package com.maninder.marvelcomics.data;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Maninder on 14/11/16.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Local {

}