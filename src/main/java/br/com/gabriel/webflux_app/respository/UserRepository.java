package br.com.gabriel.webflux_app.respository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import br.com.gabriel.webflux_app.models.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	
	public Mono<User>save(final User user){
		return mongoTemplate.save(user);
	}
}
