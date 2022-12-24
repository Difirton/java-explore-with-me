package ru.practicum.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.repository.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
