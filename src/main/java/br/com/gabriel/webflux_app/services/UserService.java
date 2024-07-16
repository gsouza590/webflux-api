package br.com.gabriel.webflux_app.services;

import org.springframework.stereotype.Service;

import br.com.gabriel.webflux_app.mapper.UserMapper;
import br.com.gabriel.webflux_app.models.User;
import br.com.gabriel.webflux_app.models.request.UserRequest;
import br.com.gabriel.webflux_app.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	private final UserMapper mapper;

	public Mono<User> save(final UserRequest request) {
		return repository.save(mapper.toEntity(request));
	}

}
