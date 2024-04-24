package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.utils.CommandConstants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;
@Component
@Slf4j
public class ViewMyLocation implements Command{
    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        Long chatId = update.getMessage().getChatId();
        Location location = update.getMessage().getLocation();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Локация установлена");

        @NonNull Double latitude = location.getLatitude();
        @NonNull Double longitude = location.getLongitude();
        log.info("Обработана локация: " + latitude + " " + longitude);

        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(String.valueOf(chatId));
        sendLocation.setLatitude(latitude);
        sendLocation.setLongitude(longitude);

        sendLocation.setReplyMarkup(createReplyKeyboardMarkup());
        return List.of(sendMessage, sendLocation);
    }

    private ReplyKeyboardMarkup createReplyKeyboardMarkup(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("Сохранить локацию");
        firstKeyboardRow.add("Удалить локацию");

        KeyboardRow secondKeyboardRow = new KeyboardRow();
        secondKeyboardRow.add(CommandConstants.RETURN_MAIN_MENU);
        replyKeyboardMarkup.setKeyboard(List.of(firstKeyboardRow,secondKeyboardRow));
        return replyKeyboardMarkup;
    }
}
