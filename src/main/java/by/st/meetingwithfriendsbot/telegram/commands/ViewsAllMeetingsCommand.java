package by.st.meetingwithfriendsbot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ViewsAllMeetingsCommand implements Command{
    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Встреси на сегодня либо выбранный день");
        sendMessage.setReplyMarkup(getKeyboard());
        return sendMessage;
    }

    public ReplyKeyboardMarkup getKeyboard() {
        // Создаем список кнопок
        List<String> setKeyboard = List.of(
                "Тут может быть имя обьекта встречи",
                "Тут может быть имя обьекта встречи",
                "Тут может быть имя обьекта встречи"
        );

        // Создаем строку клавиатуры и добавляем кнопки
        KeyboardRow key = new KeyboardRow();
        KeyboardRow secondKey = new KeyboardRow();
        for (String button : setKeyboard) {
            key.add(button);
        }
        secondKey.add("Вернуться в главное меню ");

        // Создаем клавиатуру и добавляем строку
        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(key);
        keyboard.add(secondKey);

        // Создаем разметку клавиатуры и устанавливаем клавиатуру
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(keyboard);

        return markup;
    }

}
