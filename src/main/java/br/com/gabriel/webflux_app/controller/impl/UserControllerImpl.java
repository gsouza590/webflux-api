package br.com.gabriel.webflux_app.controller.impl;

import br.com.gabriel.webflux_app.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabriel.webflux_app.controller.UserController;
import br.com.gabriel.webflux_app.models.request.UserRequest;
import br.com.gabriel.webflux_app.models.response.UserResponse;
import br.com.gabriel.webflux_app.services.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserControllerImpl implements UserController {

	private final UserService service;
	private final UserMapper mapper;

	@Override
	public ResponseEntity<Mono<Void>> save(UserRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request).then());
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> findById(String id) {
		return ResponseEntity.ok().body(service.findById(id).map(mapper::toResponse));
	}

	@Override
	public ResponseEntity<Flux<UserResponse>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Mono<UserResponse>> update(String id, UserRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Mono<Void>> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
