package br.com.gabriel.webflux_app.models.request;

import br.com.gabriel.webflux_app.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @TrimString
        @Size(min = 3, max = 50, message = "Must bt between 3 and 50 caracters")
        @NotBlank(message = "must not be null or empty")
        String name,
        @TrimString
        @Email(message = "Invalid Email")
        @NotBlank(message = "must not be null or empty")
        String email,
        @TrimString
        @Size(min = 3, max = 20, message = "Must bt between 3 and 20 caracters")
        @NotBlank(message = "must not be null or empty")
        String password) {

}
