package org.turkovaleksey.emailservice.services.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class MailCreatePeriod {
    @Schema(description = "All emails from date YYYY-MM-DD", example = "2023-01-01")
    private LocalDate start;
    @Schema(description = "All emails by date YYYY-MM-DD", example = "2023-01-10")
    private LocalDate end;

    public MailCreatePeriod() {
    }

    public MailCreatePeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailCreatePeriod that = (MailCreatePeriod) o;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "start=" + start + ", end=" + end;
    }
}
