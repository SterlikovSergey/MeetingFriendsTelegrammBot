package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.config.ApiEndpoints;
import by.st.meetingwithfriendsbot.model.FAQ;
import by.st.meetingwithfriendsbot.service.impl.FaqApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FaqApiClientImpl implements FaqApiClient {

    private final RestTemplateHelper restTemplateHelper;

    public List<FAQ> getAllFaq() {
        FAQ[] listFaq = restTemplateHelper.getForObject(ApiEndpoints.FAQ_API, FAQ[].class);
        return Arrays.asList(listFaq);
    }


    @Override
    public List<FAQ> getFaqByCategoryId(String id) {
        List<FAQ> allFaq = getAllFaq();
        return allFaq.stream()
                .filter(faq -> id.equals(faq.getCategory()))
                .collect(Collectors.toList());
    }
}
