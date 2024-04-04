package by.st.meetingwithfriendsbot.model;

import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Callback {
    private CallbackType callbackType;

    private String data;
}
