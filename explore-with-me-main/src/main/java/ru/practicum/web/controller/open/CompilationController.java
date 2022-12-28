package ru.practicum.web.controller.open;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.web.dto.compilation.CompilationDto;
import ru.practicum.web.dto.compilation.convertor.CompilationToCompilationDtoConvertor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationController {
    @Autowired
    CompilationService compilationService;
    @Autowired
    CompilationToCompilationDtoConvertor compilationToCompilationDtoConvertor;

    private final Integer DEFAULT_FROM = 0;
    private final Integer DEFAULT_SIZE = 10;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam Optional<Boolean> pinned,
                                                @RequestParam Optional<Integer> from,
                                                @RequestParam Optional<Integer> size) {
        return compilationService.findCompilations(pinned, from.orElse(DEFAULT_FROM),
                        size.orElse(DEFAULT_SIZE)).stream()
                .map(compilationToCompilationDtoConvertor::convert)
                .collect(toList());
    }

    @GetMapping("/{compilationId}")
    public CompilationDto getCompilation(@PathVariable Long compilationId) {
        return compilationToCompilationDtoConvertor.convert(compilationService.findCompilationById(compilationId));
    }
}
