package org.example.jakartaeemicroserv.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(Transactional.TxType.REQUIRED)
public class Bookshelf {

    @Inject
    EntityManager entityManager;
    @Inject
    private Logger logger;

    public Collection<Book> findAll() {
        logger.log(Level.INFO, "Find all books");
        TypedQuery<Book> findAll = entityManager.createNamedQuery(Book.FIND_ALL, Book.class);
        return Collections.unmodifiableCollection(findAll.getResultList());
    }

    public Book findByISBN(String isbn) {
        logger.log(Level.INFO, "Find book with ISBN {0}", isbn);
        return entityManager.find(Book.class, Objects.requireNonNull(isbn));
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void create(Book book) {
        Objects.requireNonNull(book);
        logger.log(Level.INFO, "Creating {0}", book);
        entityManager.persist(book);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(String isbn) {
        Objects.requireNonNull(isbn);
        logger.log(Level.INFO, "Deleteing book with ISBN {0}", isbn);
        Book reference = entityManager.getReference(Book.class, isbn);
        entityManager.remove(reference);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void update(String isbn, Book book) {
        Objects.requireNonNull(book);
        logger.log(Level.INFO, "Updating {0} using ISBN {1}", new Object[]{book, isbn});
        entityManager.merge(book);
    }
}
