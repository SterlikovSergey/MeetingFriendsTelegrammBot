package by.st.meetingwithfriendsbot.config;

public class ApiEndpoints {
    public static final String MEETINGS_API = "https://q11.jvmhost.net/events/meetings/all";

    public static final String VISA_API = "https://q11.jvmhost.net/root/visa";

    public static final String FAQ_API = "https://q11.jvmhost.net/events/faq/bot";

    public static String getMeetingApiBy(String id) {
        return "https://q11.jvmhost.net/events/meeting/" + id;
    }

    public static String getFaqApiBy(String id) {
        return FAQ_API + id;
    }

    public static String getVisaApiBy(String id) {
        return VISA_API + id;
    }
}
