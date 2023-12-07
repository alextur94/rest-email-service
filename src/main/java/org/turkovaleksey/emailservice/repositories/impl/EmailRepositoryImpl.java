package org.turkovaleksey.emailservice.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.turkovaleksey.emailservice.repositories.api.IEmailRepository;
import org.turkovaleksey.emailservice.repositories.model.Email;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;

import java.util.List;

/**
 * The type Email repository.
 */
@Repository
public class EmailRepositoryImpl implements IEmailRepository {
    private static final Logger logger = LoggerFactory.getLogger(EmailRepositoryImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Email repository.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired
    public EmailRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves all emails from the database.
     *
     * @return A List of Email objects representing all emails in the database.
     * @see Email
     */
    @Override
    public List<Email> findAll() {
        logger.debug("findAll() do work");
        logger.debug("findAll() getting all emails from db");
        return jdbcTemplate.query("SELECT id, create_date, email FROM emails", new BeanPropertyRowMapper<>(Email.class));
    }

    /**
     * Retrieves all emails within the specified MailCreatePeriod from the database.
     *
     * @param mailCreatePeriod The MailCreatePeriod defining the period for which emails are to be retrieved.
     * @return A List of Email objects representing all emails within the specified period.
     * @see Email
     * @see MailCreatePeriod
     */
    @Override
    public List<Email> findAllByPeriod(MailCreatePeriod mailCreatePeriod) {
        logger.debug("findAllByPeriod() with MailCreatePeriod = {}", mailCreatePeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", mailCreatePeriod.getStart());
        params.addValue("endDate", mailCreatePeriod.getEnd());
        logger.debug("findAllByPeriod() getting all emails by period from db");
        return jdbcTemplate.query("SELECT * FROM emails WHERE create_date BETWEEN :startDate AND :endDate", params, new BeanPropertyRowMapper<>(Email.class));
    }

    /**
     * Saves the provided Email object to the database.
     *
     * @param currentEmail The Email object to be saved.
     * @see Email
     */
    @Override
    public void save(Email currentEmail) {
        logger.debug("save() with Email = {}", currentEmail);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("createDate", currentEmail.getCreateDate());
        params.addValue("email", currentEmail.getEmail());
        logger.debug("save() insert Email = {} to db", currentEmail);
        jdbcTemplate.update("INSERT INTO emails (create_date, email) VALUES (:createDate, :email)", params);
    }
}
