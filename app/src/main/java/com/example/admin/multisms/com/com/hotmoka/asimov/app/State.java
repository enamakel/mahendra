package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The annotation for fields of Asimov activities that automatically
 * recreates the value of those fields during the lifecycle of the activities.
 * The field must have a basic or parcelable type or an array or collection of such.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface State {
}