package br.com.grandcharles.sgw.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/* Poderei usar a anotação em atributo, método, outras anotações*/
/* Ler em tempo de execução */

/* @Pattern( regexp="([a-zA-Z]{2}\\d{6,6})?" )   "[A-Z]{2}\\d{6,6}" */  
/* 2 letras, digito, minimo 6, maximo 6, com mensagem  - Expressões Regulares */ 


@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={})
@Pattern(regexp="([a-zA-Z]{2}\\d{6,6})?")
public @interface SKU {

	/* Sobrepoe a mensagem do @Pattern*/
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{br.com.grandcharles.constraints.SKU.message}";
	
	/* Agrupando validações*/
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
