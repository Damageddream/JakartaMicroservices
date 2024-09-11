package org.example.jakartaeemicroserv.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class Library {

    @Inject
    private EntityManager entityManager;
    @Inject
    private Logger logger;

    public void returnBook(String isbn, String loanId) {
        logger.log(Level.INFO, "Returning book with ISBN {0} on loan ID {1}", new Object[]{isbn});
        Book book = entityManager.getReference(Book.class, isbn);
        book.removeLoan(new Loan(loanId));
    }

    public void lendBook(String isbn, Loan loan) {
        logger.log(Level.INFO, "Lend book with ISBN {0} on {1}", new Object[]{isbn, loan});
        Book book = entityManager.getReference(Book.class, isbn);
        book.addLoan(loan);
    }

    public Loan loanInfo(String loanId) {
        logger.log(Level.INFO, "Getting loan with ID {0}", loanId);
        return entityManager.find(Loan.class, loanId);
        //return entityManager.getReference(Loan.class, loanId);
    }
}
