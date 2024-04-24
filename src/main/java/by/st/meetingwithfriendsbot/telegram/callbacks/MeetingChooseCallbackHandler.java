package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
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

            sendMessage.setParseMode("HTML");
            /*  <b>...</b> используется для отображения текста жирным шрифтом.
                <i>...</i> используется для отображения текста курсивом.
                <a href=\"...\">...</a> используется для создания гиперссылки.
                <code>...</code> используется для отображения моноширинного текста,
                который обычно используется для кода.   */
            sendMessage.setText("<b>Описание встречи: </b>" + meeting.getDescription());


            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(String.valueOf(chatId));

            Double longitude = meeting.getLongitude();
            Double latitude = meeting.getLatitude();

            SendLocation sendLocation = new SendLocation();
            sendLocation.setChatId(chatId);
            sendLocation.setLatitude(latitude);
            sendLocation.setLongitude(longitude);

            try {
                sendPhoto.setPhoto(new InputFile(String.valueOf(new URL(meeting.getPhoto()))));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            messages.add(sendMessage);
            messages.add(sendPhoto);
            messages.add(sendLocation);
        }
        return messages;
    }
}
