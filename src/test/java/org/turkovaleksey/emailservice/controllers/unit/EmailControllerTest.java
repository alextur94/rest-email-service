package org.turkovaleksey.emailservice.controllers.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.turkovaleksey.emailservice.controllers.EmailController;
import org.turkovaleksey.emailservice.services.api.IEmailService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @Mock
    private IEmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @Test
    void saveEmail() {
        String email = "example@gmail.com";
        doNothing().when(emailService).save(email);
        emailController.saveEmail(email);
        verify(emailService).save(email);
    }


    @Test
    void downloadEmailsCsv() {

    }

    @Test
    void testDownloadEmailsCsv() {
    }
}