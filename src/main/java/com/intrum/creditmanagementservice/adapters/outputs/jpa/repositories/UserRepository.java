package com.intrum.creditmanagementservice.adapters.outputs.jpa.repositories;

import com.intrum.creditmanagementservice.adapters.outputs.jpa.entities.UserEntity;
import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.Password;
import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.domains.Username;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByCustomerIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByUsernameRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RegisterUserRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveUserByUsernameRepository;
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
public class UserRepository implements RegisterUserRepository, FindUserByUsernameRepository,
        FindUserByCustomerIdRepository, RemoveUserByUsernameRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findUser(Username username) {
        Instant start = Instant.now();
        String qlString = "select u from UserEntity u where u.username =: username";
        TypedQuery<UserEntity> query = entityManager.createQuery(qlString, UserEntity.class);
        query.setParameter("username", username.getValue());
        try {
            UserEntity userEntity = query.getSingleResult();
            return Optional.ofNullable(this.mapFromEntity(userEntity));
        } catch (Exception e) {
            log.warn("User by username [{}] not found in DB!", username::getValue);
            return Optional.empty();
        } finally {
            log.debug("Find user by username [{}] took [{}]ms", username.getValue(), Duration.between(start, Instant.now()).toMillis());
        }
    }

    @Override
    public void registerUser(User user) {
        Instant start = Instant.now();
        UserEntity userEntity = new UserEntity()
                .setCustomerId(user.getCustomerId().getValue())
                .setUsername(user.getUsername().getValue())
                .setPasswordHash(user.getPassword().getValue());
        entityManager.persist(userEntity);
        log.debug("Registering new user took [{}]ms", () -> Duration.between(start, Instant.now()).toMillis());
    }

    @Override
    public Optional<User> findUser(CustomerId customerId) {
        Instant start = Instant.now();
        String qlString = "select u from UserEntity u where u.customerId =: customerId";
        TypedQuery<UserEntity> query = entityManager.createQuery(qlString, UserEntity.class);
        query.setParameter("customerId", customerId.getValue());

        try {
            UserEntity userEntity = query.getSingleResult();
            return Optional.ofNullable(this.mapFromEntity(userEntity));
        } catch (Exception e) {
            log.warn("User by customerId [{}] not found id DB!", customerId::getValue);
            return Optional.empty();
        } finally {
            log.debug("Find user by customerId [{}] took [{}]ms", customerId.getValue(), Duration.between(start, Instant.now()).toMillis());
        }
    }

    @Override
    public int removeUser(Username username) {
        Instant start = Instant.now();
        String qlString = "delete from UserEntity u where u.username =: username";
        Query query = entityManager.createQuery(qlString);
        query.setParameter("username", username.getValue());

        int deletedRecords = query.executeUpdate();

        log.debug("Remove user by username [{}] took [{}]ms", username.getValue(), Duration.between(start, Instant.now()).toMillis());
        log.debug("[{}]x user records were deleted", () -> deletedRecords);
        return deletedRecords;
    }

    private User mapFromEntity(UserEntity userEntity) {
        if (Objects.isNull(userEntity)) return null;
        return new User(
                new CustomerId(userEntity.getCustomerId()),
                new Username(userEntity.getUsername()),
                new Password(userEntity.getPasswordHash()));
    }
}
