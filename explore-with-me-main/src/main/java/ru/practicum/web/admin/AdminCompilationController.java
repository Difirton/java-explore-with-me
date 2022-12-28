package ru.practicum.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.repository.Compilation;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.web.dto.compilation.CompilationDto;
import ru.practicum.web.dto.compilation.convertor.CompilationDtoToCompilationConvertor;
import ru.practicum.web.dto.compilation.convertor.CompilationToCompilationDtoConvertor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;
    private final CompilationDtoToCompilationConvertor compilationDtoToCompilationConvertor;
    private final CompilationToCompilationDtoConvertor compilationToCompilationDtoConvertor;


    @PostMapping
    CompilationDto createCompilation(@RequestBody CompilationDto compilationDto) {
        Compilation newCompilation = compilationService.createCompilation(
                compilationDtoToCompilationConvertor.convert(compilationDto));
        return compilationToCompilationDtoConvertor.convert(newCompilation);
    }

    @DeleteMapping("/{compId}")
    void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    void deleteEventFromCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    void addEventToCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    void unpinCompilation(@PathVariable Long compId) {
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    void pinCompilation(@PathVariable Long compId) {
        compilationService.pinCompilation(compId);
    }
}
