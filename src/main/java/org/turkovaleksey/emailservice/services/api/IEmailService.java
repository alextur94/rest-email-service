package org.turkovaleksey.emailservice.services.api;

import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;

public interface IEmailService {
    String getEmailsAsCsv();

    String getEmailsByPeriodAsCsv(MailCreatePeriod mailCreatePeriod);

    void save(String email);
}
