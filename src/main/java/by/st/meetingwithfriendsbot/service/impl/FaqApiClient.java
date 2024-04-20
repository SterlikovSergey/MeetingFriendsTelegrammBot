package by.st.meetingwithfriendsbot.service.impl;

import by.st.meetingwithfriendsbot.model.FAQ;

import java.util.List;

public interface FaqApiClient {

    List<FAQ> getAllFaq();

    List<FAQ> getFaqByCategoryId(String id);
}
