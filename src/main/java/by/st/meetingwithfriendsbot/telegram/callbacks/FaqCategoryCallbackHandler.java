package by.st.meetingwithfriendsbot.telegram.callbacks;

import by.st.meetingwithfriendsbot.model.Callback;
import by.st.meetingwithfriendsbot.model.FAQ;
import by.st.meetingwithfriendsbot.service.impl.FaqApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FaqCategoryCallbackHandler implements CallbackCommand {

    private final FaqApiClient faqApiClient;

    @Override
    public List<PartialBotApiMethod<?>> apply(Callback callback, Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<PartialBotApiMethod<?>> messages = new ArrayList<>();

        List<FAQ> listFaq = faqApiClient.getFaqByCategoryId(String.valueOf(callback.getData()));
        if (listFaq != null && !listFaq.isEmpty()) {
            for (FAQ faqs : listFaq) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(chatId));
                sendMessage.setText(String.format("Вопрос : " +
                        faqs.getQuestion().toUpperCase()) + " \n " +
                        ("Ответ: " + faqs.getAnswer().toUpperCase()));
                messages.add(sendMessage);
            }
        }
        return messages;
    }
}
