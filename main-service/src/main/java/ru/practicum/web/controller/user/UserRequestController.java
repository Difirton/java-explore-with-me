package ru.practicum.web.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.service.RequestService;
import ru.practicum.web.dto.request.RequestDto;
import ru.practicum.web.dto.request.convertor.RequestToRequestDtoConvertor;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Validated
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UserRequestController {
    private final RequestService requestService;
    private final RequestToRequestDtoConvertor requestToRequestDtoConvertor;

    @PostMapping
    RequestDto createRequest(@Valid @Positive @PathVariable Long userId,
                             @Valid @Positive @RequestParam(name = "eventId") Long eventId) {
        return requestToRequestDtoConvertor.convert(requestService.createRequest(userId, eventId));
    }

    @GetMapping
    List<RequestDto> getRequests(@Valid @Positive @PathVariable Long userId) {
        return requestService.findAllUserRequests(userId).stream()
                .map(requestToRequestDtoConvertor::convert)
                .collect(toList());
    }

    @PatchMapping("{requestId}/cancel")
    RequestDto deleteRequest(@Valid @Positive @PathVariable Long userId,
                             @Valid @Positive @PathVariable Long requestId) {
        return requestToRequestDtoConvertor.convert(requestService.cancelRequest(userId, requestId));
    }
}
