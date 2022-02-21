package com.intrum.creditmanagementservice.adapters.outputs.jpa.repositories;

import com.intrum.creditmanagementservice.adapters.outputs.jpa.entities.DebtCaseEntity;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Repository
public class DebtCaseRepository implements AddDebtCaseRepository, EditDebtCaseRepository,
        FindDebtCaseRepository, ObtainDebtCasesRepository, RemoveDebtCaseRepository,
        FindDebtCaseByNumberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DebtCase addDebtCase(DebtCase debtCase) {
        Instant start = Instant.now();
        DebtCaseEntity debtCaseEntity = new DebtCaseEntity()
                .setCustomerId(debtCase.getCustomerId().getValue())
                .setNumber(debtCase.getNumber().getValue())
                .setAmount(debtCase.getMoney().getValue())
                .setCurrencyCode(debtCase.getMoney().getCurrencyCode())
                .setDueDate(debtCase.getDueDate().getValue());
        entityManager.persist(debtCaseEntity);
        log.debug("Add new debt case took [{}]ms", () -> Duration.between(start, Instant.now()).toMillis());
        return this.mapFromEntity(debtCaseEntity);
    }

    @Override
    public DebtCase editDebtCase(DebtCase debtCase) {
        Instant start = Instant.now();
        DebtCaseEntity debtCaseEntity = new DebtCaseEntity()
                .setId(debtCase.getId().getValue())
                .setCustomerId(debtCase.getCustomerId().getValue())
                .setNumber(debtCase.getNumber().getValue())
                .setAmount(debtCase.getMoney().getValue())
                .setCurrencyCode(debtCase.getMoney().getCurrencyCode())
                .setDueDate(debtCase.getDueDate().getValue());
        DebtCase updatedDebtCase = this.mapFromEntity(entityManager.merge(debtCaseEntity));
        log.debug("Edit debt case took [{}]ms", () -> Duration.between(start, Instant.now()).toMillis());
        return updatedDebtCase;
    }

    @Override
    public Optional<DebtCase> findDebtCase(DebtCaseId debtCaseId) {
        Instant start = Instant.now();
        DebtCaseEntity debtCaseEntity = entityManager.find(DebtCaseEntity.class, debtCaseId.getValue());
        log.debug("Find debt case by debt case ID [{}] took [{}]ms", debtCaseId.getValue(), Duration.between(start, Instant.now()).toMillis());
        return Optional.ofNullable(this.mapFromEntity(debtCaseEntity));
    }

    @Override
    public Set<DebtCase> obtainDebtCases(CustomerId customerId) {
        Instant start = Instant.now();
        String qlString = "select d from DebtCaseEntity d where d.customerId =: customerId";
        TypedQuery<DebtCaseEntity> query = entityManager.createQuery(qlString, DebtCaseEntity.class);
        query.setParameter("customerId", customerId.getValue());
        List<DebtCaseEntity> debtCaseEntities = query.getResultList();
        log.debug("By customer id [{}] were found [{}]x debt case records", customerId.getValue(), debtCaseEntities.size());
        log.debug("Obtain debt cases took [{}]ms", () -> Duration.between(start, Instant.now()).toMillis());
        return debtCaseEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Override
    public int removeDebtCase(DebtCaseId debtCaseId) {
        Instant start = Instant.now();
        String qlString = "delete from DebtCaseEntity d where d.id =: debtCaseId";
        Query query = entityManager.createQuery(qlString);
        query.setParameter("debtCaseId", debtCaseId.getValue());

        int deletedRecords = query.executeUpdate();
        log.debug("Remove debt case by id [{}] took [{}]ms", debtCaseId.getValue(), Duration.between(start, Instant.now()).toMillis());
        log.debug("[{}]x debt case records were deleted", () -> deletedRecords);
        return deletedRecords;
    }

    @Override
    public Optional<DebtCase> findDebtCase(CaseNumber caseNumber) {
        Instant start = Instant.now();
        String qlString = "select d from DebtCaseEntity d where upper(d.number) =: caseNumber";
        TypedQuery<DebtCaseEntity> query = entityManager.createQuery(qlString, DebtCaseEntity.class);
        query.setParameter("caseNumber", caseNumber.getValue());

        try {
            DebtCaseEntity debtCaseEntity = query.getSingleResult();
            return Optional.ofNullable(this.mapFromEntity(debtCaseEntity));
        } catch (Exception e) {
            log.warn(e);
            return Optional.empty();
        } finally {
            log.debug("Find debt case by number [{}] took [{}]ms", caseNumber.getValue(), Duration.between(start, Instant.now()).toMillis());
        }
    }

    private DebtCase mapFromEntity(DebtCaseEntity debtCaseEntity) {
        if (Objects.isNull(debtCaseEntity)) return null;
        return new DebtCase(
                new DebtCaseId(debtCaseEntity.getId()),
                new CustomerId(debtCaseEntity.getCustomerId()),
                new CaseNumber(debtCaseEntity.getNumber()),
                new Money(debtCaseEntity.getAmount(), debtCaseEntity.getCurrencyCode()),
                new DueDate(debtCaseEntity.getDueDate()));
    }
}
