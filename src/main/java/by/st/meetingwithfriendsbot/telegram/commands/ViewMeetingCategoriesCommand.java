package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.service.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ViewMeetingCategoriesCommand implements Command {
    private final MeetingApiClient meetingApiClient;

    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        List<Meeting> meetings = meetingApiClient.getAllMeetings();

        Long chatId = update.getMessage().getChatId();
        List<PartialBotApiMethod<?>> messages = new ArrayList<>();

        Map<String, List<Meeting>> meetingsByCategory = meetings.stream()
                .collect(Collectors.groupingBy(m -> m.getCategory().getName()));

        for (Map.Entry<String, List<Meeting>> entry : meetingsByCategory.entrySet()) {
            String categoryName = entry.getKey();
            List<Meeting> meetingsInCategory = entry.getValue();

            SendMessage categoryMessage = new SendMessage();
            categoryMessage.setChatId(String.valueOf(chatId));
            categoryMessage.setText("В категории " + categoryName.toUpperCase() + " создано " + meetingsInCategory.size() + " встреч ");

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Выбрать категорию " + categoryName.toUpperCase());
            inlineKeyboardButton.setCallbackData(CallbackType.CATEGORY_CHOOSE + ":" + meetingsInCategory.get(0).getCategory().getId());
            rowInline.add(inlineKeyboardButton);
            log.info(inlineKeyboardButton.getCallbackData() + " Saved in button value ");
            rowsInline.add(rowInline);
            inlineKeyboardMarkup.setKeyboard(rowsInline);

            categoryMessage.setReplyMarkup(inlineKeyboardMarkup);
            messages.add(categoryMessage);
        }

        return messages;
    }

}
