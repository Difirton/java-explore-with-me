package ru.practicum.repository.entity;

import lombok.*;
import ru.practicum.common.validator.IpAddress;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoints_hits")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @IpAddress
    @Column(name = "ip")
    private String ip;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
