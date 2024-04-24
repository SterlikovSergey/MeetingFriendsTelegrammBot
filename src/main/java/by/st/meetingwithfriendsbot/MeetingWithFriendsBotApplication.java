package by.st.meetingwithfriendsbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("by.st.meetingwithfriendsbot.model")
public class MeetingWithFriendsBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingWithFriendsBotApplication.class, args);
    }
}
