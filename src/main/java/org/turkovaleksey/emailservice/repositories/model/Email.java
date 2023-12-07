package org.turkovaleksey.emailservice.repositories.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Email {
    private Long id;
    @Schema(description = "Create Date", example = "2023-01-01")
    private LocalDate createDate;
    @Schema(description = "Email", example = "example@email.com")
    private String email;

    public Email() {
    }

    public Email(String email) {
        this.createDate = LocalDate.now();
        this.email = email;
    }

    public Email(LocalDate createDate, String email) {
        this.createDate = createDate;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return id.equals(email1.id) && createDate.equals(email1.createDate) && email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate, email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", email='" + email + '\'' +
                '}';
    }
}
