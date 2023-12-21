package org.example.annotataion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * marked mapstruct using where the construction is creating object.
 *
 * @author violet
 * @since 2023/5/5
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.CONSTRUCTOR)
public @interface Default {
}
