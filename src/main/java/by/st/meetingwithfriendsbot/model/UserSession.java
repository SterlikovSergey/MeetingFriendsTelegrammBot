package by.st.meetingwithfriendsbot.model;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private String categoryId;
    private int currentMeetingIndex;
}
