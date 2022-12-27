package ru.practicum.web.dto.user.convertor;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.user.repository.entity.User;
import ru.practicum.web.dto.user.UserDto;

public class UserDtoToUserConvertor implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto source) {
        return User.builder()
                .id(source.getId())
                .email(source.getEmail())
                .name(source.getName())
                .build();
    }
}
