package org.turkovaleksey.emailservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.turkovaleksey.emailservice.services.dto.MailCreatePeriod;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.turkovaleksey.emailservice.services.api.IMessage.SUCCESS_SAVE_EMAIL;

@SpringBootTest
@AutoConfigureMockMvc
class TestEmailController {

    @Autowired
    private EmailController emailController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveEmail_OnValide() throws Exception {
        String email = "test@" + Math.random() + ".com";
        assertThat(emailController).isNotNull();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/email/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(SUCCESS_SAVE_EMAIL));
    }

    @Test
    void testSaveEmail_ThrowDaoException() throws Exception {
        String email = "email1@example.com";
        assertThat(emailController).isNotNull();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/email/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testDownloadEmailsCsvWithAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/email/download")
                        .header("Authorization", "Basic " + getBase64Credentials("user", "12345")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.header().string("Content-Disposition", "attachment; filename=emails.csv"));
    }

    @Test
    void testDownloadEmailsCsvByPeriodAndAuthentication() throws Exception {
        MailCreatePeriod mailCreatePeriod = new MailCreatePeriod();
        mailCreatePeriod.setStart(LocalDate.of(2023, 12, 01));
        mailCreatePeriod.setEnd(LocalDate.of(2023, 12, 31));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/email/download-period")
                        .param("startDate", mailCreatePeriod.getStart().toString())
                        .param("endDate", mailCreatePeriod.getEnd().toString())
                        .header("Authorization", "Basic " + getBase64Credentials("user", "12345")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.header().string("Content-Disposition", "attachment; filename=emails_with_period.csv"));
    }

    private String getBase64Credentials(String username, String password) {
        String credentials = username + ":" + password;
        return new String(java.util.Base64.getEncoder().encode(credentials.getBytes()));
    }

}
