package ru.practicum.web.dto.request.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.request.repository.Request;
import ru.practicum.web.dto.request.RequestDto;

@Component
public class RequestToRequestDtoConvertor implements Converter<Request, RequestDto> {
    @Override
    public RequestDto convert(Request source) {
        return RequestDto.builder()
                .id(source.getId())
                .event(source.getEvent().getId())
                .requester(source.getRequester().getId())
                .status(source.getStatus())
                .created(source.getCreated())
                .build();
    }
}
