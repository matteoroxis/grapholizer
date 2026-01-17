package it.matteoroxis.grapholizer.query;

import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.UserDTO;
import it.matteoroxis.grapholizer.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserQueryResolverUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserQueryResolver userQueryResolver;

    private User user1;
    private User user2;

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
    }

    @Test
    void users_shouldReturnAllUsersAsDTO() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> result = userQueryResolver.users();

        assertThat(result).hasSize(2);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void users_shouldReturnEmptyListWhenNoUsers() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        List<UserDTO> result = userQueryResolver.users();

        assertThat(result).isEmpty();
        verify(userService).getAllUsers();
    }

    @Test
    void users_shouldMapUsersToDTO() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1));

        List<UserDTO> result = userQueryResolver.users();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("user-1");
        assertThat(result.get(0).getName()).isEqualTo("Mario Rossi");
        assertThat(result.get(0).getEmail()).isEqualTo("mario.rossi@example.com");
    }

    @Test
    void userById_shouldReturnUserWhenFound() {
        when(userService.getUserById("user-1")).thenReturn(user1);

        User result = userQueryResolver.userById("user-1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("user-1");
        assertThat(result.getName()).isEqualTo("Mario Rossi");
        assertThat(result.getEmail()).isEqualTo("mario.rossi@example.com");
    }

    @Test
    void userById_shouldCallServiceWithCorrectId() {
        when(userService.getUserById("user-1")).thenReturn(user1);

        userQueryResolver.userById("user-1");

        verify(userService, times(1)).getUserById("user-1");
        verifyNoMoreInteractions(userService);
    }

    @Test
    void userById_shouldReturnNullWhenUserNotFound() {
        when(userService.getUserById("unknown-id")).thenReturn(null);

        User result = userQueryResolver.userById("unknown-id");

        assertThat(result).isNull();
        verify(userService).getUserById("unknown-id");
    }
}
