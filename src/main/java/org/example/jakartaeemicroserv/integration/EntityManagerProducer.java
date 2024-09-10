package org.example.jakartaeemicroserv.integration;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.SynchronizationType;

public class EntityManagerProducer {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager(SynchronizationType.SYNCHRONIZED);
    }

    public void disposeEntityManager(@Disposes EntityManager entityManager) {
        entityManager.close();
    }
}
