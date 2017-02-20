package com.nerdery.umbrella.dependencies;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface AppScope {
}
