package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;
import java.util.Map;

@Component
public class CallbackCommandsHandler {
    private final Map<CallbackType, CallbackCommand> callbackHandlers;

    public CallbackCommandsHandler(
            @Autowired MeetingChooseCallbackHandler meetingChooseCallbackHandler,
            @Autowired CategoryChooseCallbackHandler categoryChooseCallbackHandler,
            @Autowired ViewMeetingLocationCallbackHandler viewMeetingLocationCallbackHandler
    ) {
        this.callbackHandlers = Map.of(
                CallbackType.MEETING_CHOOSE, meetingChooseCallbackHandler,
                CallbackType.CATEGORY_CHOOSE,categoryChooseCallbackHandler,
                CallbackType.MEETING_LOCATION,viewMeetingLocationCallbackHandler
        );
    }

    public List<PartialBotApiMethod<?>> handleCallbackCommands(Update update) {
        String callBackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String[] parts = callBackData.split(":", 2);
        CallbackType callbackType = CallbackType.valueOf(parts[0]);
        String id = parts[1];
        Callback callback = new Callback(callbackType, id);
        var callbackCommandHandler = callbackHandlers.get(callbackType);
        if (callbackCommandHandler != null) {
            return callbackCommandHandler.apply(callback, update);
        }
        return List.of(new SendMessage(String.valueOf(chatId), CommandConstants.INCORRECT_COMMAND));
    }
}
