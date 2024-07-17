package br.com.gabriel.webflux_app.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// definindo uma anotação

@Constraint(validatedBy = {TrimStringValidator.class})
@Target(FIELD)
@Retention(RUNTIME)
public @interface TrimString {

    // metodo mensagem que vai devolver uma string e caso não passe uma messagem, é definido uma padrão
    String message() default " Fiel cannot have blank spaces";

    // Devolve um array de classes chamada groups lista de grupos de validação. Permite que uma mesma anotação seja utilizada em diversos contextos
    Class<?>[]groups() default {};

    // Elemento payload uma lista de classes que define que podem ser utilizadas para adicionar informações na anotação
    Class<? extends Payload>[] payload() default {};
}
