package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.model.UserEntity;
import by.st.meetingwithfriendsbot.service.UserService;
import by.st.meetingwithfriendsbot.utils.CommandConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
@Slf4j
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final UserService userService;
    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        User user = update.getMessage().getFrom();
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        boolean userExists = userService.isUserExistsByTelegramId(user.getId());
        UserEntity userEntity;
        if(!userExists){
            userEntity = userService.createUserEntity(user);
            sendMessage.setText("Добро пожаловать " + userEntity.getName());
        } else {
            userEntity = userService.findUserByTelegram(user.getId());
            sendMessage.setText("С возвращением " + userEntity.getName());
        }
        addKeyboard(sendMessage);
        return List.of(sendMessage);
    }

    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
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
