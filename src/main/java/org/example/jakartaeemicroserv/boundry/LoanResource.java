package org.example.jakartaeemicroserv.boundry;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.example.jakartaeemicroserv.domain.Book;
import org.example.jakartaeemicroserv.domain.Bookshelf;
import org.example.jakartaeemicroserv.domain.Library;
import org.example.jakartaeemicroserv.domain.Loan;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
public class LoanResource {
    @Inject
    private Bookshelf bookshelf;

    @Inject
    private Library library;

    @Inject
    private Logger logger;

    private String isbn;

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loans() {
        logger.log(Level.INFO, "Getting loans for book with ISBN {0}", isbn);
        Book book = bookshelf.findByISBN(isbn);
        return Response.ok(book.getLoans()).build();
    }

    @GET
    @Path("/{loanId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loan(@PathParam("loanId") String loanId) {
        Loan loan = library.loanInfo(loanId);
        return Response.ok(loan).build();
    }

    @DELETE
    @Path("{loanId}")
    public Response returnBook(@PathParam("loanId") String loanId) {
        library.returnBook(isbn, loanId);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response lendBook(Loan loan) {
        library.lendBook(isbn, loan);
        URI location = UriBuilder.fromResource(BookResource.class)
                .path("/{isbn}/loans/{loanId}")
                .resolveTemplate("isbn", isbn)
                .resolveTemplate("loanId", loan.getId())
                .build();
        return Response.created(location).build();
    }
}
