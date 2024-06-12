package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.model.FAQ;
import by.st.meetingwithfriendsbot.model.enums.CallbackType;
import by.st.meetingwithfriendsbot.service.impl.FaqApiClientImpl;
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
public class ViewFaqCategoriesCommand implements Command {

    private final FaqApiClientImpl faqApiClient;

    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        List<FAQ> listFaq = faqApiClient.getAllFaq();

        Long chatId = update.getMessage().getChatId();
        List<PartialBotApiMethod<?>> messages = new ArrayList<>();

        Map<String, List<FAQ>> faqByCategory = listFaq.stream()
                .collect(Collectors.groupingBy(FAQ::getCategory));

        for (Map.Entry<String, List<FAQ>> entry : faqByCategory.entrySet()) {
            String categoryName = entry.getKey();
            List<FAQ> faqInCategory = entry.getValue();

            SendMessage categoryMessage = new SendMessage();
            categoryMessage.setChatId(String.valueOf(chatId));
            categoryMessage.setText("В категории " + categoryName.toUpperCase() + " создано " + faqInCategory.size() + " вопросов ");

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Выбрать категорию " + categoryName.toUpperCase());
            inlineKeyboardButton.setCallbackData(CallbackType.FAQ_CATEGORY + ":" + faqInCategory.get(0).getCategory());
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
