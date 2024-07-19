package br.com.gabriel.webflux_app.controller;

import br.com.gabriel.webflux_app.mapper.UserMapper;
import br.com.gabriel.webflux_app.models.User;
import br.com.gabriel.webflux_app.models.request.UserRequest;
import br.com.gabriel.webflux_app.models.response.UserResponse;
import br.com.gabriel.webflux_app.services.UserService;
import com.mongodb.reactivestreams.client.MongoClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static reactor.core.publisher.Mono.just;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerImplTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private MongoClient mongoClient;


    @Test
    @DisplayName("Test endpoint save with Success")
    void testSaveWithSuccess() {
        UserRequest request = new UserRequest("Gabriel","gabriel@gmail.com","123");
        when(service.save(any(UserRequest.class))).thenReturn(just(User.builder().build()));

        webTestClient.post().uri("/users")
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isCreated();

        verify(service,times(1)).save(any(UserRequest.class));
    }

    @Test
    @DisplayName("Test endpoint save with Success")
    void testSaveWithBadRequest() {
        UserRequest request = new UserRequest("Gabriel ","gabriel@gmail.com","123");

        webTestClient.post().uri("/users")
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.path").isEqualTo("/users")
                .jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
                .jsonPath("$.error").isEqualTo("Validation Error")
                .jsonPath("$.message").isEqualTo("Error on Validation attributes")
                .jsonPath("$.errors[0].fieldName").isEqualTo("name")
                .jsonPath("$.errors[0].message").isEqualTo("Field cannot have blank spaces");
    }

    @Test
    @DisplayName("Test FindById With Success")
    void testFindByIdWithSuccess() {

        final UserResponse userResponse = new UserResponse("12345","Gabriel","gabriel@gmail.com","123");
        when(service.findById(anyString())).thenReturn(just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri("/users/12345").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("12345")
                .jsonPath("$.name").isEqualTo("Gabriel")
                .jsonPath("$.email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.password").isEqualTo("123");
    }

    @Test
    @DisplayName("Test find all with Success")
    void testFindAllWithSuccess() {
        final UserResponse userResponse = new UserResponse("12345","Gabriel","gabriel@gmail.com","123");
        when(service.findAll()).thenReturn(Flux.just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.get().uri("/users").accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("12345")
                .jsonPath("$.[0].name").isEqualTo("Gabriel")
                .jsonPath("$.[0].email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.[0].password").isEqualTo("123");
    }

    @Test
    @DisplayName("Test update with success")
    void testUpdateWithSuccess() {
        final UserRequest request = new UserRequest("Gabriel ","gabriel@gmail.com","12345");
        final UserResponse userResponse = new UserResponse("12345","Gabriel","gabriel@gmail.com","123");
        when(service.update(anyString(),any(UserRequest.class))).thenReturn(just(User.builder().build()));
        when(mapper.toResponse(any(User.class))).thenReturn(userResponse);

        webTestClient.patch().uri("/users/12345")
                .contentType(APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus().isOk().expectBody()
                .jsonPath("$.id").isEqualTo("12345")
                .jsonPath("$.name").isEqualTo("Gabriel")
                .jsonPath("$.email").isEqualTo("gabriel@gmail.com")
                .jsonPath("$.password").isEqualTo("123");

        verify(service).update(anyString(),any(UserRequest.class));
        verify(mapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Test delete with success")
    void testDeleteWithSuccess() {
        when(service.delete(anyString())).thenReturn(just(User.builder().build()));

        webTestClient.delete().uri("/users/12345")
                .exchange()
                .expectStatus().isOk();
        verify(service).delete(anyString());

    }
}