package ru.practicum.web.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Email
    @Size(max = 100)
    private String email;
}
