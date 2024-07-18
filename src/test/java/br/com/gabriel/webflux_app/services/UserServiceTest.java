package br.com.gabriel.webflux_app.services;

import br.com.gabriel.webflux_app.mapper.UserMapper;
import br.com.gabriel.webflux_app.models.User;
import br.com.gabriel.webflux_app.models.request.UserRequest;
import br.com.gabriel.webflux_app.respository.UserRepository;
import br.com.gabriel.webflux_app.services.exceptions.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    @Test
    void testSave() {
        UserRequest request = new UserRequest("Gabriel", "gabriel@gmail.com", "123");
        User user = User.builder().build();

        when(mapper.toEntity(any(UserRequest.class))).thenReturn(user);
        when(repository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));

        Mono<User> result = service.save(request);

        StepVerifier.create(result).expectNextMatches(Objects::nonNull).expectComplete().verify();

        Mockito.verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void testFindById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(User.builder().id("1234").build()));

        Mono<User> result = service.findById("123");

        StepVerifier.create(result).expectNextMatches(user -> user.getClass() == User.class && user.getId() == "1234" && user != null).expectComplete().verify();

        Mockito.verify(repository, times(1)).findById(anyString());
    }

    @Test
    void testFindByAll() {
        when(repository.findAll()).thenReturn(Flux.just(User.builder().build()));

        Flux<User> result = service.findAll();

        StepVerifier.create(result).expectNextMatches(user -> user.getClass() == User.class).expectComplete().verify();

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        UserRequest request = new UserRequest("Gabriel", "gabriel@gmail.com", "123");
        User user = User.builder().build();

        when(mapper.toEntity(any(UserRequest.class), any(User.class))).thenReturn(user);
        when(repository.findById(anyString())).thenReturn(Mono.just(user));
        when(repository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> result = service.update("123", request);

        StepVerifier.create(result).expectNextMatches(Objects::nonNull).expectComplete().verify();

        Mockito.verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void testDelete() {
        User user = User.builder().build();
        when(repository.findAndRemove(anyString())).thenReturn(Mono.just(user));
        Mono<User> result = service.delete("123");

        StepVerifier.create(result).expectNextMatches(Objects::nonNull).expectComplete().verify();

        Mockito.verify(repository, times(1)).findAndRemove(anyString());
    }

    @Test
    void testHandleNotFound(){
        when(repository.findById(anyString())).thenReturn(Mono.empty());

        try{
            service.findById("123").block();
        }catch (Exception ex){
           assertEquals(ObjectNotFoundException.class,ex.getClass());
           assertEquals(format("Object not found, Id: %s , Type: %s", "123", User.class.getSimpleName()),ex.getMessage());
        }
    }
}
