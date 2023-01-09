package ru.practicum.web.dto.like.convertor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.like.repository.entity.Like;
import ru.practicum.web.dto.event.convertor.EventDtoConvertor;
import ru.practicum.web.dto.like.LikeDto;
import ru.practicum.web.dto.user.convertor.UserToUserDtoConvertor;

@Component
@RequiredArgsConstructor
public class LikeToLikeDtoConvertor implements Converter<Like, LikeDto> {
    private final UserToUserDtoConvertor userToUserDtoConvertor;
    private final EventDtoConvertor eventDtoConvertor;

    @Override
    public LikeDto convert(Like source) {
        return LikeDto.builder()
                .id(source.getId())
                .reviewer(userToUserDtoConvertor.convert(source.getReviewer()))
                .event(eventDtoConvertor.convertToDto(source.getEvent()))
                .build();
    }
}
