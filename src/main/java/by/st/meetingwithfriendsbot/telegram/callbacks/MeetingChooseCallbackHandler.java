package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MeetingChooseCallbackHandler implements CallbackCommand {
    private final MeetingApiClient meetingApiClient;

    @Override
    public List<PartialBotApiMethod<?>> apply(Callback callback, Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<PartialBotApiMethod<?>> messages = new ArrayList<>();

        Meeting meeting = meetingApiClient.getMetingBy(callback.getData());

        if (meeting != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText("Описание встречи: " + meeting.getDescription());
            messages.add(sendMessage);

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(chatId));
            try {
                sendPhoto.setPhoto(new InputFile(String.valueOf(new URL(meeting.getPhoto()))));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            messages.add(sendPhoto);
        }
        return messages;
    }
}
