package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.config.ApiEndpoints;
import by.st.meetingwithfriendsbot.model.Meeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingApiClientImpl implements MeetingApiClient {
    private final RestTemplateHelper restTemplateHelper;

    @Override
    public List<Meeting> getAllMeetings() {
        Meeting[] meetings = restTemplateHelper.getForObject(ApiEndpoints.MEETINGS_API, Meeting[].class);
        return Arrays.asList(meetings);
    }

    @Override
    public Meeting createMeeting(Meeting newMeeting) {
        return null;
    }

    @Override
    public Meeting getMetingBy(String id) {
        return restTemplateHelper.getForObject(ApiEndpoints.getMeetingApiBy(id), Meeting.class);
    }

    @Override
    public List<Meeting> getMeetingsByCategoryId(String id) {
        List<Meeting> allMeetings = getAllMeetings();
        return allMeetings.stream()
                .filter(meeting -> id.equals(meeting.getCategory().getId().toString()))
                .collect(Collectors.toList());
    }
}
