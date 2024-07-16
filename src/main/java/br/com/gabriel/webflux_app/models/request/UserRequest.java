package br.com.gabriel.webflux_app.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @Size(min = 3, max = 50, message = "Must bt between 3 and 50 caracters")
        @NotBlank(message = "must not be null or empty")
        String name,
        @Email(message = "Invalid Email")
        @NotBlank(message = "must not be null or empty")
        String email,
        @Size(min = 3, max = 20, message = "Must bt between 3 and 20 caracters")
        @NotBlank(message = "must not be null or empty")
        String password) {

}
