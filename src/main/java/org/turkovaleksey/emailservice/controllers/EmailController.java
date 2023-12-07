package org.turkovaleksey.emailservice.controllers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.turkovaleksey.emailservice.repositories.exceptions.AppError400;
import org.turkovaleksey.emailservice.services.api.IEmailService;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;

import static org.turkovaleksey.emailservice.services.api.IMessage.SUCCESS_SAVE_EMAIL;

/**
 * The type Email controller.
 */
@RestController
@RequestMapping("/api/email")
@OpenAPIDefinition(
        info = @Info(
                title = "Email Rest Api",
                description = "REST API for freelance. Working with emails", version = "1.0.0",
                contact = @Contact(
                        name = "Turkov Aleksey",
                        email = "alextur94@gamil.com"
                )
        )
)
@Tag(name = "Email controller", description = "Controller for saving email and obtaining a list of all saved emails for authorized users")
public class EmailController {
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
    private final IEmailService emailService;

    /**
     * Instantiates a new Email controller.
     *
     * @param emailService the email service
     */
    @Autowired
    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Saves the provided email address and returns a ResponseEntity with a success message.
     *
     * @param email The email address to be saved.
     * @return A ResponseEntity with a success message.
     * @see ResponseEntity
     */
    @Operation(summary = "Saving email to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV file with all emails",
                    content = {@Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AppError400.class))})
    })
    @PostMapping("/save")
    public ResponseEntity<?> saveEmail(@RequestBody String email) {
        logger.debug("saveEmail() with email = {}", email);
        logger.debug("saveEmail() call save() with email = {}", email);
        emailService.save(email);
        return ResponseEntity.ok(SUCCESS_SAVE_EMAIL);
    }

    /**
     * Downloads the emails in CSV format and returns a ResponseEntity containing the CSV file.
     *
     * @return A ResponseEntity<byte[]> with the CSV file content.
     * @see ResponseEntity
     */
    @Operation(summary = "Receiving a file with a list of saved emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV file with all emails",
                    content = {@Content(mediaType = "application/octet-stream")})
    })
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadEmailsCsv() {
        logger.debug("downloadEmailsCsv() do work");
        logger.debug("downloadEmailsCsv() call getEmailsAsCsv()");
        String csvContent = emailService.getEmailsAsCsv();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "emails.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent.getBytes());
    }

    /**
     * Downloads the emails within the specified MailCreatePeriod in CSV format
     * and returns a ResponseEntity containing the CSV file.
     *
     * @param mailCreatePeriod The MailCreatePeriod defining the period for which emails are to be downloaded.
     * @return A ResponseEntity<byte[]> with the CSV file content.
     * @see MailCreatePeriod
     * @see ResponseEntity
     */
    @Operation(summary = "Receiving a file with a list of saved emails by period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV file with emails by period",
                    content = {@Content(mediaType = "application/octet-stream")})
    })
    @GetMapping("/download-period")
    public ResponseEntity<byte[]> downloadEmailsCsvByPeriod(@RequestBody MailCreatePeriod mailCreatePeriod) {
        logger.debug("downloadEmailsCsv() with MailCreatePeriod = {}", mailCreatePeriod);
        logger.debug("downloadEmailsCsv() call getEmailsByPeriodAsCsv() with = {}", mailCreatePeriod);
        String csvContent = emailService.getEmailsByPeriodAsCsv(mailCreatePeriod);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "emails_with_period.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvContent.getBytes());
    }
}
