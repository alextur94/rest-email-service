package org.turkovaleksey.emailservice.repositories.api;

import org.turkovaleksey.emailservice.repositories.model.Email;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;

import java.util.List;

public interface IEmailRepository {
    List<Email> findAll();

    List<Email> findAllByPeriod(MailCreatePeriod mailCreatePeriod);

    void save(Email email);
}
