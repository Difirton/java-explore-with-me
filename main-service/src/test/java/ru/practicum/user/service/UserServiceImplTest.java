package ru.practicum.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.repository.entity.User;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {
    private User user;
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository mockRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .name("Test")
                .build();
    }

    @Test
    @DisplayName("Test find page users by id")
    void testFindUsers() {
        when(mockRepository.findUsersByIdIn(List.of(1L), PageRequest.of(0, 5))).thenReturn(List.of(user));
        userService.findUsers(List.of(1L), 0, 5);
        verify(mockRepository, times(1)).findUsersByIdIn(List.of(1L), PageRequest.of(0, 5));
    }

    @Test
    @DisplayName("Test create new User")
    void testCreateUser() {
        when(mockRepository.save(user)).thenReturn(user);
        userService.createUser(user);
        verify(mockRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Delete user by id")
    void deleteUser() {
        userService.deleteUser(1L);
        verify(mockRepository, times(1)).deleteById(1L);
    }
}