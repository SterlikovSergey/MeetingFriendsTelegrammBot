package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.model.FAQ;

import java.util.List;

public interface FaqApiClient {

    List<FAQ> getAllFaq();

    List<FAQ> getFaqByCategoryId(String id);
}
