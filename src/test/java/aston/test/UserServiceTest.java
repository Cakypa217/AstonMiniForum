package aston.test;

import aston.dto.UserDto;
import aston.entity.User;
import aston.repository.UserRepository;
import aston.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser_shouldReturnUserDto_whenUserExists() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUser_shouldThrowException_whenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void createUser_shouldReturnUserDto_whenUserIsValid() {
        UserDto userDto = UserDto.builder()
                .name("New User")
                .email("new@example.com")
                .build();

        User user = User.builder()
                .name("New User")
                .email("new@example.com")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name("New User")
                .email("new@example.com")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New User", result.getName());
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_shouldReturnUpdatedUserDto_whenUserExists() {
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
                .name("Updated User")
                .email("updated@example.com")
                .build();

        User existingUser = User.builder()
                .id(userId)
                .name("Original User")
                .email("original@example.com")
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .name("Updated User")
                .email("updated@example.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDto result = userService.updateUser(userId, userDto);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Updated User", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_shouldThrowException_whenUserDoesNotExist() {
        Long userId = 1L;
        UserDto userDto = UserDto.builder()
                .name("Updated User")
                .email("updated@example.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, userDto);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_shouldCallRepositoryDelete_whenUserExists() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .name("Test User")
                .email("test@example.com")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_shouldThrowException_whenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }
}