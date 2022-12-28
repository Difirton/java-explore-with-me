package ru.practicum.request.repository;

import lombok.*;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.request.constant.Status;
import ru.practicum.user.repository.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
}
