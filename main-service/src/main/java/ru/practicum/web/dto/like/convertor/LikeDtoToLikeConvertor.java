package ru.practicum.web.dto.like.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.like.repository.entity.Like;
import ru.practicum.user.repository.entity.User;
import ru.practicum.web.dto.like.LikeDto;

@Component
public class LikeDtoToLikeConvertor implements Converter<LikeDto, Like> {
    @Override
    public Like convert(LikeDto source) {
        return Like.builder()
                .id(source.getId())
                .isLike(source.getIsLike())
                .reviewer(User.builder()
                        .id(source.getReviewer().getId())
                        .build())
                .event(Event.builder()
                        .id(source.getEvent().getId())
                        .build())
                .build();
    }
}
