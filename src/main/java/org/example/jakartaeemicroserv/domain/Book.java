package org.example.jakartaeemicroserv.domain;

import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NamedQuery(name = Book.FIND_ALL, query = "SELECT b FROM Book b")
@JsonbPropertyOrder(value = {"isbn", "title", "author"})
public class Book {
    static final String FIND_ALL = "Book.findAll";

    @Id
    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Embedded
    private Author author;

    public Book() {
    }

    public Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans.clear();
        this.loans.addAll(loans);
    }

    public void addLoan(Loan loan) {
        loan.setBook(this);
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        int index = loans.indexOf(loan);
        if (index > -1) {
            Loan l = loans.remove(index);
            l.setBook(null);
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
