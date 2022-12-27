package ru.practicum.web.dto.request.convertor;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.request.repository.Request;
import ru.practicum.web.dto.request.RequestDto;

public class RequestToRequestDtoConvertor implements Converter<Request, RequestDto> {
    @Override
    public RequestDto convert(Request source) {
        return RequestDto.builder()
                .id(source.getId())
                .event(source.getEvent().getId())
                .requester(source.getRequester().getId())
                .state(source.getState())
                .created(source.getCreated())
                .build();
    }
}
