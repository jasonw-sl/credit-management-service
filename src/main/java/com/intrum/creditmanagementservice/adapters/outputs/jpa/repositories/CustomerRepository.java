package com.intrum.creditmanagementservice.adapters.outputs.jpa.repositories;

import com.intrum.creditmanagementservice.adapters.outputs.jpa.entities.CustomerEntity;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.ports.outputs.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Repository
public class CustomerRepository implements AddCustomerRepository, FindCustomerByIdRepository,
        RemoveCustomerByIdRepository, EditCustomerRepository, FindCustomerByCodeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer addCustomer(Customer customer) {
        Instant start = Instant.now();
        CustomerEntity customerEntity = new CustomerEntity()
                .setName(customer.getName().getValue())
                .setSurname(customer.getSurname().getValue())
                .setCode(customer.getCode().getValue())
                .setCountry(customer.getCountry().getValue())
                .setEmail(customer.getEmail().getValue());
        entityManager.persist(customerEntity);
        log.debug("Add new customer to DB took [{}]ms", () -> Duration.between(start, Instant.now()).toMillis());
        return this.mapFromEntity(customerEntity);
    }

    @Override
    public Optional<Customer> findCustomer(CustomerId customerId) {
        Instant start = Instant.now();
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, customerId.getValue());
        log.debug("Find customer by customer id [{}] took [{}]ms", customerId.getValue(), Duration.between(start, Instant.now()).toMillis());
        return Optional.ofNullable(this.mapFromEntity(customerEntity));
    }

    @Override
    public int removeCustomer(CustomerId customerId) {
        Instant start = Instant.now();
        String qlString = "delete from CustomerEntity c where c.id =: customerId";
        Query query = entityManager.createQuery(qlString);
        query.setParameter("customerId", customerId.getValue());

        int deletedRecords = query.executeUpdate();

        log.debug("Remove customer by id [{}] took [{}]ms", customerId.getValue(), Duration.between(start, Instant.now()).toMillis());
        log.debug("[{}]x customer records were deleted", () -> deletedRecords);
        return deletedRecords;
    }

    @Override
    public Customer editCustomer(Customer customer) {
        Instant start = Instant.now();
        CustomerEntity customerEntity = new CustomerEntity()
                .setId(customer.getId().getValue())
                .setName(customer.getName().getValue())
                .setSurname(customer.getSurname().getValue())
                .setCode(customer.getCode().getValue())
                .setCountry(customer.getCountry().getValue())
                .setEmail(customer.getEmail().getValue());
        entityManager.merge(customerEntity);
        log.debug("Edit customer data took [{}]ms", () -> Duration.between(start, Instant.now()).toMillis());
        return this.mapFromEntity(customerEntity);
    }

    @Override
    public Optional<Customer> findCustomer(Code code) {
        Instant start = Instant.now();
        String qlString = "select c from CustomerEntity c where c.code =: code";
        TypedQuery<CustomerEntity> query = entityManager.createQuery(qlString, CustomerEntity.class);
        query.setParameter("code", code.getValue());

        try {
            CustomerEntity customerEntity = query.getSingleResult();
            return Optional.ofNullable(this.mapFromEntity(customerEntity));
        } catch (Exception e) {
            log.warn(e);
            return Optional.empty();
        } finally {
            log.debug("Find customer by code [{}] took [{}]ms", code.getValue(), Duration.between(start, Instant.now()).toMillis());
        }
    }

    private Customer mapFromEntity(CustomerEntity customerEntity) {
        if (Objects.isNull(customerEntity)) return null;
        return new Customer(
                new CustomerId(customerEntity.getId()),
                new Name(customerEntity.getName()),
                new Surname(customerEntity.getSurname()),
                new Code(customerEntity.getCode()),
                new Country(customerEntity.getCountry()),
                new Email(customerEntity.getEmail()));
    }
}
