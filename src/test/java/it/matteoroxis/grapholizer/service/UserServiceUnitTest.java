package it.matteoroxis.grapholizer.service;

import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.CreateUser;
import it.matteoroxis.grapholizer.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;
    private CreateUser createUserInput;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId("user-1");
        user1.setName("Mario Rossi");
        user1.setEmail("mario.rossi@example.com");

        user2 = new User();
        user2.setId("user-2");
        user2.setName("Luigi Verdi");
        user2.setEmail("luigi.verdi@example.com");

        createUserInput = new CreateUser();
        createUserInput.setName("Giovanni Bianchi");
        createUserInput.setEmail("giovanni.bianchi@example.com");
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(Arrays.asList(user1, user2));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_shouldReturnEmptyListWhenNoUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.getAllUsers();

        assertThat(result).isEmpty();
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_shouldReturnUserWhenFound() {
        when(userRepository.findById("user-1")).thenReturn(Optional.of(user1));

        User result = userService.getUserById("user-1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("user-1");
        assertThat(result.getName()).isEqualTo("Mario Rossi");
        assertThat(result.getEmail()).isEqualTo("mario.rossi@example.com");
        verify(userRepository).findById("user-1");
    }

    @Test
    void getUserById_shouldThrowExceptionWhenNotFound() {
        when(userRepository.findById("unknown-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById("unknown-id"))
            .isInstanceOf(NoSuchElementException.class);

        verify(userRepository).findById("unknown-id");
    }

    @Test
    void createUser_shouldCreateAndReturnUser() {
        User savedUser = new User();
        savedUser.setId("user-3");
        savedUser.setName("Giovanni Bianchi");
        savedUser.setEmail("giovanni.bianchi@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(createUserInput);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("user-3");
        assertThat(result.getName()).isEqualTo("Giovanni Bianchi");
        assertThat(result.getEmail()).isEqualTo("giovanni.bianchi@example.com");

        verify(userRepository).save(argThat(user ->
            user.getName().equals("Giovanni Bianchi") &&
            user.getEmail().equals("giovanni.bianchi@example.com")
        ));
    }

    @Test
    void createUser_shouldCallRepositoryWithCorrectInput() {
        User savedUser = new User();
        savedUser.setId("user-3");
        savedUser.setName("Giovanni Bianchi");
        savedUser.setEmail("giovanni.bianchi@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.createUser(createUserInput);

        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUser_shouldMapInputFieldsCorrectly() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.createUser(createUserInput);

        assertThat(result.getName()).isEqualTo(createUserInput.getName());
        assertThat(result.getEmail()).isEqualTo(createUserInput.getEmail());
    }
}

