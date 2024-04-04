package by.st.meetingwithfriendsbot.service;

import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.repository.MeetingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public List<Meeting> findAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting findMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId).orElseThrow();
    }

    public void updateMeeting(Long meetingId, Meeting updatedMeeting) {

        meetingRepository.findById(meetingId)
                .map(existingMeeting -> {
                    /*if (updatedMeeting.getTitle() != null) {
                        existingMeeting.setTitle(updatedMeeting.getTitle());
                    }
                    if (updatedMeeting.getDescription() != null) {
                        existingMeeting.setDescription(updatedMeeting.getDescription());
                    }
                    if (updatedMeeting.getDateTime() != null) {
                        existingMeeting.setDateTime(updatedMeeting.getDateTime());
                    }*/
                    return meetingRepository.save(existingMeeting);
                })
                .orElseThrow(() -> new EntityNotFoundException("Meeting not found with id " + meetingId));
    }
}
