package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.utils.CommandConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class StartCommand implements Command {
    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(CommandConstants.GREETINGS);
        addKeyboard(sendMessage);
        return List.of(sendMessage);
    }

    @Override
    public SendMessage applySendMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(CommandConstants.GREETINGS);
        addKeyboard(sendMessage);
        return sendMessage;
    }

    private void addKeyboard(SendMessage sendMessage) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow firstLayer = new KeyboardRow();
        firstLayer.add(CommandConstants.MEETING_CATEGORIES);
        firstLayer.add(CommandConstants.FAQ);
        firstLayer.add(CommandConstants.CREATE_MEETING);
        keyboardRows.add(firstLayer);

        KeyboardButton locationButton = new KeyboardButton(CommandConstants.MY_LOCATION);
        locationButton.setRequestLocation(true);

        KeyboardButton contactButton = new KeyboardButton("My Contact Phone");
        contactButton.setRequestContact(true);

        KeyboardRow secondLayer = new KeyboardRow();
        secondLayer.add("Админка");
        secondLayer.add(locationButton);
        secondLayer.add(contactButton);
        keyboardRows.add(secondLayer);

        KeyboardRow thirdLayer = new KeyboardRow();
        thirdLayer.add(CommandConstants.CHECK_REGISTRATION);
        thirdLayer.add(CommandConstants.GET_VISA);
        keyboardRows.add(thirdLayer);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
}
