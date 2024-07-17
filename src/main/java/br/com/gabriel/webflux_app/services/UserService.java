package br.com.gabriel.webflux_app.services;

import br.com.gabriel.webflux_app.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gabriel.webflux_app.mapper.UserMapper;
import br.com.gabriel.webflux_app.models.User;
import br.com.gabriel.webflux_app.models.request.UserRequest;
import br.com.gabriel.webflux_app.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final UserMapper mapper;

	public Mono<User> save(final UserRequest request) {
		return repository.save(mapper.toEntity(request));
	}

	public Mono<User>findById(final String id){return repository.findById(id).switchIfEmpty(Mono.error(new ObjectNotFoundException(format(
			"Object not found, Id: %s , Tupe: %s", id, User.class.getSimpleName()))));}
}
