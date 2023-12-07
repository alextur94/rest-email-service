package org.turkovaleksey.emailservice.services.api;

public interface IMessage {
    String SUCCESS_SAVE_EMAIL = "Your email has been successfully saved";
    String CONVERT_LIST_TO_CSV_EXCEPTION = "Failed to convert email list to csv file";
    String INCORRECT_DATE_EXCEPTION = "The date must be greater than or equal to the start date";
}
