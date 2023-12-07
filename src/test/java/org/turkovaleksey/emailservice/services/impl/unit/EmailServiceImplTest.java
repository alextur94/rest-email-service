package org.turkovaleksey.emailservice.services.impl.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.turkovaleksey.emailservice.repositories.api.IEmailRepository;
import org.turkovaleksey.emailservice.repositories.model.Email;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;
import org.turkovaleksey.emailservice.services.exceptions.IncorrectPeriodException;
import org.turkovaleksey.emailservice.services.impl.EmailServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {
    @InjectMocks
    private EmailServiceImpl emailService;
    @Mock
    private IEmailRepository emailRepository;

    @Test
    public void testGetEmailsAsCsv() {
        List<Email> mockEmails = new ArrayList<>();
        mockEmails.add(new Email(LocalDate.of(2023, 1, 1), "test1@example.com"));
        mockEmails.add(new Email(LocalDate.of(2023, 1, 2), "test2@example.com"));
        when(emailRepository.findAll()).thenReturn(mockEmails);
        String csvResult = emailService.getEmailsAsCsv();
        String expectedCsv = emailService.convertListAsCsv(mockEmails);
        assertEquals(expectedCsv, csvResult);
    }

    @Test
    public void testGetEmailsByPeriodAsCsv() {
        MailCreatePeriod mailCreatePeriod = new MailCreatePeriod();
        mailCreatePeriod.setStart(LocalDate.of(2023, 01, 01));
        mailCreatePeriod.setEnd(LocalDate.of(2023, 01, 10));
        List<Email> mockEmails = new ArrayList<>();
        mockEmails.add(new Email(LocalDate.of(2023, 1, 1), "test1@example.com"));
        mockEmails.add(new Email(LocalDate.of(2023, 1, 2), "test2@example.com"));
        mockEmails.add(new Email(LocalDate.of(2023, 1, 10), "test3@example.com"));
        when(emailRepository.findAllByPeriod(mailCreatePeriod)).thenReturn(mockEmails);
        String csvResult = emailService.getEmailsByPeriodAsCsv(mailCreatePeriod);
        String expectedCsv = emailService.convertListAsCsv(mockEmails);
        assertEquals(expectedCsv, csvResult);
    }

    @Test
    public void testSave() {
        String email = "example@test.com";
        emailService.save(email);
        verify(emailRepository, times(1)).save(any(Email.class));
    }

    @Test
    public void testCheckDateWhenIncorrectPeriodException() {
        MailCreatePeriod mailCreatePeriod = new MailCreatePeriod();
        mailCreatePeriod.setStart(LocalDate.of(2023, 1, 1));
        mailCreatePeriod.setEnd(LocalDate.of(2022, 12, 31));
        assertThrows(IncorrectPeriodException.class, () -> emailService.checkDateOnValid(mailCreatePeriod));
    }

}
