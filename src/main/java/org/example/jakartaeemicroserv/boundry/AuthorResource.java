package org.example.jakartaeemicroserv.boundry;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.jakartaeemicroserv.domain.Author;
import org.example.jakartaeemicroserv.domain.Book;

@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private final Book book;
    public AuthorResource(Book book){
        this.book = book;
    }

    @GET
    public Author get() {
        return book.getAuthor();
    }
}
