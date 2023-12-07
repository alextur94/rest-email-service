package org.turkovaleksey.emailservice.services.impl;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.turkovaleksey.emailservice.repositories.api.IEmailRepository;
import org.turkovaleksey.emailservice.repositories.model.Email;
import org.turkovaleksey.emailservice.services.api.IEmailService;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;
import org.turkovaleksey.emailservice.services.exceptions.ConvertEmailListToCSVException;
import org.turkovaleksey.emailservice.services.exceptions.IncorrectPeriodException;

import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;

import static org.turkovaleksey.emailservice.services.api.IMessage.CONVERT_LIST_TO_CSV_EXCEPTION;
import static org.turkovaleksey.emailservice.services.api.IMessage.INCORRECT_DATE_EXCEPTION;

/**
 * Service implementation for handling email-related operations.
 * The {@code EmailServiceImpl} provides methods for retrieving, saving, and manipulating
 * email data. It includes functionality to get emails in CSV format, filter emails by period,
 * save new emails, check the validity of dates, and convert a list of emails to CSV format.
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * // Instantiate the EmailServiceImpl
 * // Retrieve emails as CSV
 * // Retrieve emails for a specific period as CSV
 * // Save a new email
 * The methods in this service follow a naming convention that reflects their purpose
 * and functionality.
 * </p>
 *
 * @see Email
 */
@Service
public class EmailServiceImpl implements IEmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final IEmailRepository emailRepository;

    /**
     * Instantiates a new Email service.
     *
     * @param emailRepository the email repository
     */
    @Autowired
    public EmailServiceImpl(IEmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * Retrieves all emails from the repository and returns them in CSV format.
     *
     * @return A CSV-formatted string representing all the emails in the repository.
     * @see Email
     */
    @Override
    public String getEmailsAsCsv() {
        logger.debug("getEmailsAsCsv() do work");
        logger.debug("getEmailsAsCsv() call findAll()");
        List<Email> emails = emailRepository.findAll();
        return convertListAsCsv(emails);
    }

    /**
     * Retrieves emails for the specified period and returns them in CSV format.
     *
     * @param mailCreatePeriod The period for which emails need to be retrieved.
     * @return A CSV-formatted string representing emails for the specified period.
     * @throws IllegalArgumentException If the provided period is not valid.
     * @see MailCreatePeriod
     */
    @Override
    public String getEmailsByPeriodAsCsv(MailCreatePeriod mailCreatePeriod) {
        logger.debug("getEmailsByPeriodAsCsv() with MailCreatePeriod = {}", mailCreatePeriod);
        logger.debug("getEmailsByPeriodAsCsv() call getEmailsByPeriodAsCsv()");
        checkDateOnValid(mailCreatePeriod);
        logger.debug("getEmailsByPeriodAsCsv() call findAllByPeriod() with MailCreatePeriod = {}", mailCreatePeriod);
        List<Email> emails = emailRepository.findAllByPeriod(mailCreatePeriod);
        return convertListAsCsv(emails);
    }

    /**
     * Saves the specified email by creating a new Email object and persisting it.
     *
     * @param email The email address to be saved.
     */
    @Override
    public void save(String email) {
        logger.debug("save() with email = {}", email);
        logger.debug("save() create Email");
        Email newEmail = new Email();
        newEmail.setCreateDate(LocalDate.now());
        newEmail.setEmail(email);
        logger.debug("save() call save() in repository with Email = {}", newEmail);
        emailRepository.save(newEmail);
    }

    /**
     * Checks the validity of the provided MailCreatePeriod by ensuring that the start date
     * is not later than the end date.
     *
     * @param mailCreatePeriod The MailCreatePeriod to be validated.
     * @throws IncorrectPeriodException If the provided period has an incorrect date range
     *                                  (start date later than the end date).
     * @see MailCreatePeriod
     * @see IncorrectPeriodException
     */
    public void checkDateOnValid(MailCreatePeriod mailCreatePeriod) {
        logger.debug("checkDateOnValid() with MailCreatePeriod = {}", mailCreatePeriod);
        if (mailCreatePeriod.getStart().isAfter(mailCreatePeriod.getEnd())) {
            logger.error("checkDateOnValid() " + INCORRECT_DATE_EXCEPTION);
            throw new IncorrectPeriodException(INCORRECT_DATE_EXCEPTION);
        }
    }

    /**
     * Converts a List of Email objects into a CSV-formatted string, including headers for Date and Email.
     *
     * @param emails The List of Email objects to be converted.
     * @return A CSV-formatted string representing the provided list of Email objects.
     * @throws ConvertEmailListToCSVException If an exception occurs during the conversion process.
     * @see Email
     * @see ConvertEmailListToCSVException
     */
    public String convertListAsCsv(List<Email> emails) {
        logger.debug("convertListAsCsv() with List Emails (hashcode) = {}", emails.hashCode());
        StringWriter writer = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            String[] headers = {"Date", "Email"};
            csvWriter.writeNext(headers);
            for (Email email : emails) {
                String[] data = {
                        email.getCreateDate().toString(),
                        email.getEmail()
                };
                csvWriter.writeNext(data);
            }
            csvWriter.flush();
        } catch (Exception e) {
            logger.error("convertListAsCsv() " + CONVERT_LIST_TO_CSV_EXCEPTION);
            throw new ConvertEmailListToCSVException(CONVERT_LIST_TO_CSV_EXCEPTION);
        }
        return writer.toString();
    }
}
