package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Find customer user case")
class FindCustomerUseCaseImplTest {

    @Mock
    private FindCustomerByIdRepository findCustomerByIdRepository;

    @InjectMocks
    private FindCustomerUseCaseImpl findCustomerUseCase;

    @Test
    @DisplayName("When customer can not be found in DB")
    void test_1() {
        //given
        CustomerId customerId = new CustomerId(2246L);
        given(findCustomerByIdRepository.findCustomer(customerId))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> findCustomerUseCase.findCustomer(customerId));

        //then
        then(findCustomerByIdRepository).should().findCustomer(customerId);
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found customer by customer id [2246] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When customer is found in DB")
    void test_2() {
        //given
        CustomerId customerId = new CustomerId(2250L);
        given(findCustomerByIdRepository.findCustomer(customerId))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(2250L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv"))));

        //when
        Customer response = findCustomerUseCase.findCustomer(customerId);

        //then
        then(findCustomerByIdRepository).should().findCustomer(customerId);
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getId().getValue()).isEqualTo(2250L),
                () -> assertThat(response.getCode().getValue()).isEqualTo("28019112458")
        );
    }
}