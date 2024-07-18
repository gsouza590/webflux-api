package br.com.gabriel.webflux_app.mapper;

import br.com.gabriel.webflux_app.models.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.gabriel.webflux_app.models.User;
import br.com.gabriel.webflux_app.models.request.UserRequest;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring",
		nullValuePropertyMappingStrategy =IGNORE,
		nullValueCheckStrategy = ALWAYS
		)
public interface UserMapper {
	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request);

	// fazer uma sobrecarga
	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request, @MappingTarget final User entity);

	UserResponse toResponse(final User user);
}
