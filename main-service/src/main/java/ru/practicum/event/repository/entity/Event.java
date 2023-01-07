package ru.practicum.event.repository.entity;

import lombok.*;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.compilation.repository.Compilation;
import ru.practicum.event.repository.constant.State;
import ru.practicum.user.repository.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @NotBlank
    @Column(name = "annotation", length = 1000)
    private String annotation;

    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne
    private Category category;

    @Builder.Default
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @ManyToOne
    private User initiator;

    @Column(name = "paid")
    private Boolean isPaid;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @ManyToOne
    private Location location;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "views")
    private Integer views;

    @ManyToMany
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "compilation_id"))
    private List<Compilation> compilations;
}

