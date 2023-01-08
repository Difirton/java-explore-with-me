package ru.practicum.repository.entity;

import lombok.*;
import ru.practicum.common.validator.IpAddress;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpoints_hits")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "app")
    private String app;

    @Size(max = 500)
    @Column(name = "uri")
    private String uri;

    @IpAddress
    @Size(max = 32)
    @Column(name = "ip")
    private String ip;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
