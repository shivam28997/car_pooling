package com.shivam.repository;


import com.shivam.entity.Journey;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class CustomJourneyRepositoryImpl implements CustomJourneyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Journey> findOverlappingJourneys(Long carOwnerId, LocalDateTime startTime, LocalDateTime endTime) {
        String jpql = "SELECT j FROM Journey j WHERE j.carOwner.id = :carOwnerId AND j.status <> 'COMPLETED' " +
                "AND ((:startTime BETWEEN j.startTime AND j.endTime) OR " +
                "(:endTime BETWEEN j.startTime AND j.endTime) OR " +
                "(j.startTime BETWEEN :startTime AND :endTime) OR " +
                "(j.endTime BETWEEN :startTime AND :endTime))";
        TypedQuery<Journey> query = entityManager.createQuery(jpql, Journey.class);
        query.setParameter("carOwnerId", carOwnerId);
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        return query.getResultList();
    }
}
