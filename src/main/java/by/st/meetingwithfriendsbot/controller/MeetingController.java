package by.st.meetingwithfriendsbot.controller;

import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.service.MeetingService;
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

    @GetMapping("/")
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        List<Meeting> meetings = meetingService.findAllMeetings();
        return new ResponseEntity<>(meetings,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createMeeting(@RequestBody Meeting newMeeting) {
        Meeting meeting = meetingService.createMeeting(newMeeting);
        return new ResponseEntity<>(meeting, HttpStatus.CREATED);
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<Object> getMeetingById(@PathVariable Long meetingId) {
        Meeting meeting = meetingService.findMeetingById(meetingId);
        return new ResponseEntity<>(meeting,HttpStatus.OK);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<Object> updateMeeting(@PathVariable Long meetingId, @RequestBody Meeting updatedMeeting) {
        meetingService.updateMeeting(meetingId, updatedMeeting);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Object> deleteMeeting(@PathVariable Long meetingId) {
        /*meetingService.deleteMeeting(meetingId);*/
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
