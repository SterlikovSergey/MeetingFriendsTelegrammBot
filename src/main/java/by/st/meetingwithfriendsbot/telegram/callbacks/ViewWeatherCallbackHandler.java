package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.api.WeatherParser;
import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewWeatherCallbackHandler implements CallbackCommand {
    private final WeatherParser weatherParser;
    private final MeetingApiClient meetingApiClient;

    @Override
    public List<PartialBotApiMethod<?>> apply(Callback callback, Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Meeting meeting = meetingApiClient.getMetingBy(String.valueOf(callback.getData()));
        List<PartialBotApiMethod<?>> messages = new ArrayList<>();
        messages.add(createMessage(chatId, meeting));
        messages.add(sendLocation(meeting.getLatitude(), meeting.getLongitude(), chatId));
        messages.add(sendWeather(chatId, meeting));
        return messages;
    }

    private SendMessage createMessage(Long chatId, Meeting meeting) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setParseMode("Markdown");
        sendMessage.setText("*Адрес : *\n" + meeting.getAddress() + " 👇");
        return sendMessage;
    }

    private SendLocation sendLocation(Double latitude, Double longitude, Long chatId){
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(String.valueOf(chatId));
        sendLocation.setLatitude(latitude);
        sendLocation.setLongitude(longitude);
        return sendLocation;
    }

    private SendMessage sendWeather(Long chatId, Meeting meeting) {
        String latitude = String.valueOf(meeting.getLatitude());
        String longitude = String.valueOf(meeting.getLongitude());
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Погода по координатам: " + weatherParser.getReadyForecast(latitude, longitude));
        message.setReplyMarkup(createInlineKeyboardMarkup(meeting));
        return message;
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(Meeting meeting) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButtonBack = createInlineKeyboardButton("Обратно ",
                CallbackType.CATEGORY_CHOOSE + ":" + meeting.getCategory().getId());
        List<InlineKeyboardButton> rowInlineOne = Collections.singletonList(inlineKeyboardButtonBack);
        inlineKeyboardMarkup.setKeyboard(List.of(rowInlineOne));
        return inlineKeyboardMarkup;

    }

    private InlineKeyboardButton createInlineKeyboardButton(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);
        return inlineKeyboardButton;
    }
}
