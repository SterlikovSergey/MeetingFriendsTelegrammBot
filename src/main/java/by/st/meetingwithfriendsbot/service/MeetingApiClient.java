package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.model.Meeting;

import java.util.List;

public interface MeetingApiClient {
    List<Meeting> getAllMeetings();
    Meeting createMeeting(Meeting newMeeting);
    Meeting getMetingBy(String id);
    List<Meeting> getMeetingsByCategoryId(String id);
    List<Meeting>  getUpcomingMeetings();
}
