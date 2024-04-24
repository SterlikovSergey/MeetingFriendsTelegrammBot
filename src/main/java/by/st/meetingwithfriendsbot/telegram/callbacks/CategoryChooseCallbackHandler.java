package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.model.UserSession;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CategoryChooseCallbackHandler implements CallbackCommand {
    private final MeetingApiClient meetingApiClient;
    private final UserSession userSession;

    @Override
    public List<PartialBotApiMethod<?>> apply(Callback callback, Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (!callback.getData().equals("next") && !callback.getData().equals("prev")) {
            userSession.setCategoryId(callback.getData());
            userSession.setCurrentMeetingIndex(0);
        }
        List<Meeting> meetings = meetingApiClient.getMeetingsByCategoryId(userSession.getCategoryId());
        if (callback.getData().equals("next")) {
            userSession.setCurrentMeetingIndex(Math.min(userSession.getCurrentMeetingIndex() + 1, meetings.size() - 1));
        } else if (callback.getData().equals("prev")) {
            userSession.setCurrentMeetingIndex(Math.max(userSession.getCurrentMeetingIndex() - 1, 0));
        }
        return List.of(createMessage(chatId, meetings.get(userSession.getCurrentMeetingIndex()), meetings.size()));
    }


    private SendMessage createMessage(Long chatId, Meeting meeting, int totalMeetings) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setParseMode("Markdown");
        sendMessage.setText(String.format("*Название встречи: %s\nДата проведения мероприятия: %s*",
                meeting.getName().toUpperCase(),
                meeting.getMeetingDate()));
        sendMessage.setReplyMarkup(createInlineKeyboardMarkup(chatId, meeting, totalMeetings));

        return sendMessage;
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(Long chatId, Meeting meeting, int totalMeetings) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButtonMoreDetails = createInlineKeyboardButton("Открыть подробно ",
                CallbackType.MEETING_CHOOSE + ":" + meeting.getId());

        InlineKeyboardButton inlineKeyboardButtonLocation = createInlineKeyboardButton("Показать на карте 🗺️ ",
                CallbackType.MEETING_LOCATION + ":" + meeting.getId());

        InlineKeyboardButton inlineKeyboardButtonPhoto = createInlineKeyboardButton("Фото встречи 📸 ",
                CallbackType.MEETING_PHOTO + ":" + meeting.getId());

        InlineKeyboardButton inlineKeyboardButtonWeather = createInlineKeyboardButton("Прогноз погоды 🌤️ ",
                CallbackType.MEETING_WEATHER + ":" + meeting.getId());

        InlineKeyboardButton inlineKeyboardButtonNext = null;
        if (userSession.getCurrentMeetingIndex() < totalMeetings - 1) {
            inlineKeyboardButtonNext = createInlineKeyboardButton("Следующая 🔜",
                    CallbackType.CATEGORY_CHOOSE + ":" + "next");
        }

        InlineKeyboardButton inlineKeyboardButtonPrev = null;
        if (userSession.getCurrentMeetingIndex() > 0) {
            inlineKeyboardButtonPrev = createInlineKeyboardButton("🔙 Предыдущая",
                    CallbackType.CATEGORY_CHOOSE + ":" + "prev");
        }

        List<InlineKeyboardButton> rowInlineOne = Arrays.asList(inlineKeyboardButtonMoreDetails, inlineKeyboardButtonPhoto);
        List<InlineKeyboardButton> rowInlineSecond = Collections.singletonList(inlineKeyboardButtonLocation);
        List<InlineKeyboardButton> rowInLineThird = Collections.singletonList(inlineKeyboardButtonWeather);
        List<InlineKeyboardButton> rowInlineNav = new ArrayList<>();
        if (inlineKeyboardButtonPrev != null) rowInlineNav.add(inlineKeyboardButtonPrev);
        if (inlineKeyboardButtonNext != null) rowInlineNav.add(inlineKeyboardButtonNext);

        inlineKeyboardMarkup.setKeyboard(Arrays.asList(rowInlineOne, rowInlineSecond, rowInLineThird, rowInlineNav));

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardButton createInlineKeyboardButton(String text, String callbackData) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callbackData);

        return inlineKeyboardButton;
    }
}


