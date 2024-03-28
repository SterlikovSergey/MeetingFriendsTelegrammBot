package by.st.meetingwithfriendsbot.telegram.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class StartCommand implements Command{
    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Добро пожаловать !!!");
        addKeyboard(sendMessage);
        return sendMessage;
    }

    private void addKeyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstLayer = new KeyboardRow();
        firstLayer.add("Меню встреч");
        firstLayer.add("Вопрос/Ответ");
        firstLayer.add("Добавить встречу");
        keyboard.add(firstLayer);
        KeyboardRow secondLayer = new KeyboardRow();
        secondLayer.add("Админка");
        secondLayer.add("Навигация");
        keyboard.add(secondLayer);
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
