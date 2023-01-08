package ru.practicum.web.dto.user.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.user.repository.entity.User;
import ru.practicum.web.dto.user.UserDto;

@Component
public class UserToUserDtoConvertor implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .id(source.getId())
                .email(source.getEmail())
                .name(source.getName())
                .build();
    }
}
