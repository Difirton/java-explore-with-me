package ru.practicum.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.service.UserService;
import ru.practicum.web.dto.user.UserDto;
import ru.practicum.web.dto.user.convertor.UserDtoToUserConvertor;
import ru.practicum.web.dto.user.convertor.UserToUserDtoConvertor;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final UserToUserDtoConvertor userToUserDtoConvertor;
    private final UserDtoToUserConvertor userDtoToUserConvertor;

    @GetMapping
    List<UserDto> getUsers(@RequestParam List<Long> ids, @RequestParam Optional<Integer> from,
                           @RequestParam Optional<Integer> size) {
        return userService.findUsers(ids, from.orElse(0), size.orElse(10)).stream()
                .map(userToUserDtoConvertor::convert)
                .collect(Collectors.toList());
    }

    @PostMapping
    UserDto createUser(@RequestBody UserDto newUser) {
        return userToUserDtoConvertor.convert(userService.createUser(userDtoToUserConvertor.convert(newUser)));
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@Valid @Positive @PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
