package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.service.impl.MeetingApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Collections;
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
        Long chatId = update.getMessage().getChatId();
        List<Meeting> meetings = meetingApiClient.getUpcomingMeetings();

        Map<String, List<Meeting>> meetingsByCategory = meetings.stream()
                .collect(Collectors.groupingBy(m -> m.getCategory().getName()));

        return meetingsByCategory.entrySet().stream()
                .map(entry -> createCategoryMessage(chatId, entry))
                .collect(Collectors.toList());
    }

    private SendMessage createCategoryMessage(Long chatId, Map.Entry<String, List<Meeting>> entry) {
        String categoryName = entry.getKey();
        List<Meeting> meetingsInCategory = entry.getValue();

        SendMessage categoryMessage = new SendMessage();
        categoryMessage.setChatId(String.valueOf(chatId));
        categoryMessage.setParseMode("Markdown");
        categoryMessage.setText(String.format("*В категории %s создано %d встреч*", categoryName.toUpperCase(), meetingsInCategory.size()));
        categoryMessage.setReplyMarkup(createInlineKeyboardMarkup(categoryName, meetingsInCategory));

        return categoryMessage;
    }

    private InlineKeyboardMarkup createInlineKeyboardMarkup(String categoryName, List<Meeting> meetingsInCategory) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("Выбрать категорию " + categoryName.toUpperCase());
        inlineKeyboardButton.setCallbackData(CallbackType.CATEGORY_CHOOSE + ":" + meetingsInCategory.get(0).getCategory().getId());

        List<List<InlineKeyboardButton>> rowsInline = Collections.singletonList(Collections.singletonList(inlineKeyboardButton));
        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }

}
