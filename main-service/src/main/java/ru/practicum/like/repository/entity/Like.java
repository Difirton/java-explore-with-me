package ru.practicum.like.repository.entity;

import lombok.*;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.user.repository.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_like")
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
}
