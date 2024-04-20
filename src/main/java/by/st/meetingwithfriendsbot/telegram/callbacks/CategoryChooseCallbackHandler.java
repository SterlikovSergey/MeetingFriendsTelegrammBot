package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryChooseCallbackHandler implements CallbackCommand {
    private final MeetingApiClient meetingApiClient;

    @Override
    public List<PartialBotApiMethod<?>> apply(Callback callback, Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<PartialBotApiMethod<?>> messages = new ArrayList<>();

        // Получение списка встреч по id категории
        List<Meeting> meetings = meetingApiClient.getMeetingsByCategoryId(String.valueOf(callback.getData()));
        if (meetings != null && !meetings.isEmpty()) {
            for (Meeting meeting : meetings) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                sendMessage.setText(String.format("Название встречи: %s\nДата проведения мероприятия: %s",
                        meeting.getName().toUpperCase(),
                        meeting.getMeetingDate()));

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText("Подробнее о " + meeting.getName().toUpperCase());
                inlineKeyboardButton.setCallbackData(CallbackType.MEETING_CHOOSE + ":" + meeting.getId());
                rowInline.add(inlineKeyboardButton);
                rowsInline.add(rowInline);
                inlineKeyboardMarkup.setKeyboard(rowsInline);

                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                messages.add(sendMessage);
            }
        }
        return messages;
    }
}
