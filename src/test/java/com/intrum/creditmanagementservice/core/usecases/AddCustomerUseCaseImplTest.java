package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.AddCustomerRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Add customer use case")
class AddCustomerUseCaseImplTest {

    @Mock
    private AddCustomerRepository addCustomerRepository;

    @Mock
    private FindCustomerByCodeRepository findCustomerByCodeRepository;

    @InjectMocks
    private AddCustomerUseCaseImpl addCustomerUseCase;

    @Test
    @DisplayName("When customer already exist in DB")
    void test_1() {
        //given
        Customer customer =
                new Customer(
                        new Name("Oskars"),
                        new Surname("Adamovičs"),
                        new Code("28019112458"),
                        new Country("LV"),
                        new Email("adamovics.oskars@inbox.lv"));
        given(findCustomerByCodeRepository.findCustomer(new Code("28019112458")))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(2229L),
                                new Name("Ainārs"),
                                new Surname("Vieglais"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("vieglais.ainars@inbox.lv"))));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> addCustomerUseCase.addCustomer(customer));

        //then
        then(findCustomerByCodeRepository).should().findCustomer(new Code("28019112458"));
        then(addCustomerRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.CONFLICT),
                () -> assertThat(coreException.getObject()).isEqualTo("Customer with code [28019112458] already exist DB with customer id [2229]!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_EXIST)
        );
    }

    @Test
    @DisplayName("When customer is added successfully")
    void test_2() {
        //given
        Customer customer =
                new Customer(
                        new Name("Oskars"),
                        new Surname("Adamovičs"),
                        new Code("28019112458"),
                        new Country("LV"),
                        new Email("adamovics.oskars@inbox.lv"));
        given(findCustomerByCodeRepository.findCustomer(new Code("28019112458")))
                .willReturn(Optional.empty());
        given(addCustomerRepository.addCustomer(customer))
                .willReturn(
                        new Customer(
                                new CustomerId(2232L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv")));

        //when
        Customer response = addCustomerUseCase.addCustomer(customer);

        //then
        then(findCustomerByCodeRepository).should().findCustomer(new Code("28019112458"));
        then(addCustomerRepository).should().addCustomer(customer);
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getId().getValue()).isEqualTo(2232L)
        );
    }
}