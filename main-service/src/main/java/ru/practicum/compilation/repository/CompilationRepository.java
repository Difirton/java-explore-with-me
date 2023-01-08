package ru.practicum.compilation.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findCompilationsByPinned(Boolean pinned, PageRequest pageRequest);
}
