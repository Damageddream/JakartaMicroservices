package org.example.jakartaeemicroserv.domain;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "loan")
@JsonbPropertyOrder({"id", "username", "start", "end"})
public class Loan {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "period_start")
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate start;

    @Column(name = "period_end")
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonbTransient
    private Book book;

    public Loan() {
        this(UUID.randomUUID().toString());
    }

    public Loan(String loanId) {
        this.id = loanId;
    }

    public Loan(String username, LocalDate start, LocalDate end) {
        this();
        this.username = username;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) && Objects.equals(username, loan.username) && Objects.equals(start, loan.start) && Objects.equals(end, loan.end) && Objects.equals(book, loan.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, start, end, book);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", book=" + book +
                '}';
    }
}

