package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.config.ApiEndpoints;
import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                // Фильтрация по ID категории
                .filter(meeting -> id.equals(meeting.getCategory().getId().toString()))
                // Фильтрация по дате встречи, которая должна быть позже текущей даты
                .filter(meeting -> meeting.getMeetingDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meeting> getUpcomingMeetings() {
        List<Meeting> allMeetings = getAllMeetings();
        return allMeetings.stream()
                .filter(meeting -> meeting.getMeetingDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }
}
