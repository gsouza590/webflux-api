package br.com.gabriel.webflux_app.respository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import br.com.gabriel.webflux_app.models.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepository {

	private final ReactiveMongoTemplate mongoTemplate;
	
	public Mono<User>save(final User user){
		return mongoTemplate.save(user);
	}

    public Mono<User> findById(String id) {return mongoTemplate.findById(id,User.class);}

	public Flux<User> findAll() {
		return mongoTemplate.findAll(User.class);
	}
}
