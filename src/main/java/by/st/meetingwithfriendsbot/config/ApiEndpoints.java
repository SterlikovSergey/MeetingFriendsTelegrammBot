package by.st.meetingwithfriendsbot.config;

public class ApiEndpoints {
    public static final String MEETINGS_API = "https://q11.jvmhost.net/events/meetings/all";

    public static String getMeetingApiBy(String id) {
        return "https://q11.jvmhost.net/events/meeting/" + id;
    }
}
