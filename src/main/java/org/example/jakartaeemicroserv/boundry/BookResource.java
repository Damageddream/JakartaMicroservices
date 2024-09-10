package org.example.jakartaeemicroserv.boundry;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.example.jakartaeemicroserv.domain.Book;
import org.example.jakartaeemicroserv.domain.Bookshelf;
import org.example.jakartaeemicroserv.domain.Loan;

import java.net.URI;

@Path("books")
@RequestScoped
public class BookResource {
    @Inject
    Bookshelf bookshelf;

    @Context
    private ResourceContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response books(){
        return Response.ok(bookshelf.findAll()).build();
    }

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response book(@PathParam("isbn") String isbn ){
        Book book = bookshelf.findByISBN(isbn);
        return Response.ok(book).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book){
        bookshelf.create(book);
        URI location = UriBuilder.fromResource(BookResource.class)
                .path("/{isbn}")
                .resolveTemplate("isbn",book.getIsbn())
                .build();
        return Response.created(location).build();
    }

    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn){
        bookshelf.delete(isbn);
        return Response.ok().build();
    }

    @PUT
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book){
        bookshelf.update(isbn,book);
        return Response.ok().build();
    }

    @Path("/{isbn}/author")
    public AuthorResource author(@PathParam("isbn") String isbn) {
        Book book = bookshelf.findByISBN(isbn);
        return new AuthorResource(book);
    }

    @Path("{isbn}/loans")
    public LoanResource loans(@PathParam("isbn") String isbn){
        LoanResource loanResource = context.getResource(LoanResource.class);
        loanResource.setIsbn(isbn);

        return loanResource;
    }
 }
