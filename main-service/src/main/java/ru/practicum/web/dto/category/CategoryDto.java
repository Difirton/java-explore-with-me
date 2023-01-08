package ru.practicum.web.dto.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
}
