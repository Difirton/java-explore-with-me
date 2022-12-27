package ru.practicum.compilation.repository;

import lombok.*;
import ru.practicum.event.repository.entity.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Boolean pinned;

    @ManyToMany(mappedBy = "compilations")
    private List<Event> events;
}
