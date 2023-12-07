package org.turkovaleksey.emailservice.repositories.impl.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.turkovaleksey.emailservice.repositories.impl.EmailRepositoryImpl;
import org.turkovaleksey.emailservice.repositories.model.Email;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailRepositoryImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private EmailRepositoryImpl emailRepository;

    @Test
    void findAll() {
        List<Email> mockEmails = new ArrayList<>();
        mockEmails.add(new Email(LocalDate.of(2023, 1, 1), "test1@example.com"));
        mockEmails.add(new Email(LocalDate.of(2023, 1, 2), "test2@example.com"));
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class))).thenReturn(mockEmails);
        List<Email> actualEmails = emailRepository.findAll();
        verify(jdbcTemplate).query(eq("SELECT id, create_date, email FROM emails"),
                any(BeanPropertyRowMapper.class));
        assertEquals(mockEmails, actualEmails);
    }

    @Test
    void findAllByPeriod() {
        MailCreatePeriod mailCreatePeriod = new MailCreatePeriod();
        mailCreatePeriod.setStart(LocalDate.of(2023,01,01));
        mailCreatePeriod.setEnd(LocalDate.of(2023,01,10));
        List<Email> mockEmails = new ArrayList<>();
        mockEmails.add(new Email(LocalDate.of(2023, 1, 1), "test1@example.com"));
        mockEmails.add(new Email(LocalDate.of(2023, 1, 2), "test2@example.com"));
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(BeanPropertyRowMapper.class))).thenReturn(mockEmails);
        List<Email> actualEmails = emailRepository.findAllByPeriod(mailCreatePeriod);
        verify(jdbcTemplate).query(eq("SELECT * FROM emails WHERE create_date BETWEEN :startDate AND :endDate"), any(MapSqlParameterSource.class),
                any(BeanPropertyRowMapper.class));
        assertEquals(mockEmails, actualEmails);
    }

    @Test
    void save() {
        Email testEmail = new Email();
        testEmail.setCreateDate(LocalDate.of(2023, 01,01));
        testEmail.setEmail("test1@example.com");
        emailRepository.save(testEmail);
        MapSqlParameterSource expectedParams = new MapSqlParameterSource();
        expectedParams.addValue("createDate", testEmail.getCreateDate());
        expectedParams.addValue("email", testEmail.getEmail());
        verify(jdbcTemplate).update(
                eq("INSERT INTO emails (create_date, email) VALUES (:createDate, :email)"),
                eq(expectedParams)
        );
    }
}