package it.matteoroxis.grapholizer.mutation;

import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.model.dto.CreateUser;
import it.matteoroxis.grapholizer.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserMutationResolverUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserMutationResolver userMutationResolver;

    private CreateUser createUserInput;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        createUserInput = new CreateUser();
        createUserInput.setName("Mario Rossi");
        createUserInput.setEmail("mario.rossi@example.com");

        expectedUser = new User();
        expectedUser.setId("user-1");
        expectedUser.setName("Mario Rossi");
        expectedUser.setEmail("mario.rossi@example.com");
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        when(userService.createUser(createUserInput)).thenReturn(expectedUser);

        User result = userMutationResolver.createUser(createUserInput);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("user-1");
        assertThat(result.getName()).isEqualTo("Mario Rossi");
        assertThat(result.getEmail()).isEqualTo("mario.rossi@example.com");
    }

    @Test
    void createUser_shouldCallUserServiceWithInput() {
        when(userService.createUser(createUserInput)).thenReturn(expectedUser);

        userMutationResolver.createUser(createUserInput);

        verify(userService, times(1)).createUser(createUserInput);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void createUser_shouldPassInputCorrectly() {
        when(userService.createUser(createUserInput)).thenReturn(expectedUser);

        userMutationResolver.createUser(createUserInput);

        verify(userService).createUser(argThat(input ->
                input.getName().equals("Mario Rossi") &&
                        input.getEmail().equals("mario.rossi@example.com")
        ));
    }
}