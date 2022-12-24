package ru.practicum.event.repository.entity;

import lombok.*;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.user.repository.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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

    @Column(name = "annotation", length = 1000)
    private String annotation;

    @OneToOne
    @Column(name = "category")
    private Category category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @OneToOne
    @Column(name = "initiator")
    private User initiator;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "views")
    private Integer views;
}

