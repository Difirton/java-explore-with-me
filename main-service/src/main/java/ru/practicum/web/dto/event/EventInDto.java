package ru.practicum.web.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import ru.practicum.event.repository.constant.State;
import ru.practicum.user.repository.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInDto {
    @JsonProperty("eventId")
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 1024)
    private String annotation;

    @Size(max = 2048)
    private String description;
    private Long category;
    private Integer confirmedRequests;
    private User initiator;
    private Boolean paid;
    private Boolean requestModeration;
    private LocationDto location;
    private State state;
    private Integer participantLimit;
    private Integer views;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime publishedOn;
}
