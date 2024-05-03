package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.service.UserService;
import by.st.meetingwithfriendsbot.utils.CommandConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCommand implements Command {

    private final UserService userService;

    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        Long chatID = update.getMessage().getChatId();

        if (update.getMessage().hasContact()) {
            Contact contact = update.getMessage().getContact();
            User user = update.getMessage().getFrom();
            userService.saveUserContact(user.getId(), contact);
            log.info(String.valueOf(update.getMessage().getContact()));
            SendMessage sendMessage = createSendMessage(chatID);
            sendMessage.setText("Контакт " + contact.getPhoneNumber() + " сохранён");
            return List.of(sendMessage);
        }
        if (update.getMessage().hasLocation()) {
            Location location = update.getMessage().getLocation();
            User user = update.getMessage().getFrom();
            userService.saveUserLocation(user.getId(), location);
            SendMessage sendMessage = createSendMessage(chatID);
            sendMessage.setText("Ваша локация сохранена ");
            return List.of(sendMessage);

        }
        return List.of(createSendMessage(chatID));
    }

    private SendMessage createSendMessage(Long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text("Предлагаю вам сохранить номер телефона и вашу локацию")
                .replyMarkup(createKeyboard())
                .build();
    }

    private ReplyKeyboardMarkup createKeyboard() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton contactButton = createButton("Сохранить контакт", false, true);
        KeyboardButton locationButton = createButton("Сохранить местоположение", true, false);
        KeyboardButton  backButton = createButton(CommandConstants.RETURN_MAIN_MENU, false, false);
        KeyboardRow row = new KeyboardRow();
        row.add(contactButton);
        row.add(locationButton);
        keyboardRows.add(row);
        KeyboardRow backRow = new KeyboardRow();
        backRow.add(backButton);
        keyboardRows.add(backRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    private KeyboardButton createButton(String text, Boolean requestLocation, Boolean requestContact) {
        KeyboardButton button = new KeyboardButton(text);
        if (requestLocation != null && requestLocation) {
            button.setRequestLocation(true);
        } else if (requestContact != null && requestContact) {
            button.setRequestContact(true);
        }
        return button;
    }
}