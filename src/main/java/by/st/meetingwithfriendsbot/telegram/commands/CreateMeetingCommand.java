package by.st.meetingwithfriendsbot.telegram.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateMeetingCommand implements Command {
    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        return null;
    }

    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
    }
}