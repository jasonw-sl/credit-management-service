package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Username;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveUserByUsernameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Remove user use case")
class RemoveUserUseCaseImplTest {

    @Mock
    private RemoveUserByUsernameRepository removeUserByUsernameRepository;

    @InjectMocks
    private RemoveUserUseCaseImpl removeUserUseCase;

    @Test
    @DisplayName("When user could not be found by username")
    void test_1() {
        //given
        Username username = new Username("oskada");
        given(removeUserByUsernameRepository.removeUser(username))
                .willReturn(0);

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> removeUserUseCase.removeUser(username));

        //then
        then(removeUserByUsernameRepository).should().removeUser(username);
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found user by username [OSKADA] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(USER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When user is removed successfully")
    void test_2() {
        //given
        Username username = new Username("oskada");
        given(removeUserByUsernameRepository.removeUser(username))
                .willReturn(1);

        //when
        assertDoesNotThrow(() -> removeUserUseCase.removeUser(username));

        //then
        then(removeUserByUsernameRepository).should().removeUser(username);
    }
}