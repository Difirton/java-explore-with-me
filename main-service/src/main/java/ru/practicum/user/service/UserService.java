package ru.practicum.user.service;

import ru.practicum.user.repository.entity.User;

import java.util.List;

public interface UserService {

    List<User> findUsers(List<Long> ids, Integer from, Integer size);

    User createUser(User user);

    void deleteUser(Long userId);
}
