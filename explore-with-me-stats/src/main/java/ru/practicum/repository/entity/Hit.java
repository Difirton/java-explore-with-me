package ru.practicum.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
