package by.st.meetingwithfriendsbot.controller;

import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.service.MeetingService;
import by.st.meetingwithfriendsbot.service.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("http://localhost:8099/api/meetings")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;
    private final MeetingApiClient meetingApiClient;


    @GetMapping("/")
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        List<Meeting> meetings = meetingApiClient.getAllMeetings();
        return new ResponseEntity<>(meetings,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createMeeting(@RequestBody Meeting newMeeting) {
        Meeting meeting = meetingService.createMeeting(newMeeting);
        return new ResponseEntity<>(meeting, HttpStatus.CREATED);
    }


}
