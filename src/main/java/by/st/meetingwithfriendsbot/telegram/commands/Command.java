package by.st.meetingwithfriendsbot.telegram.commands;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface Command {
    SendMessage applySendMessage(Update update);
    List<PartialBotApiMethod<?>> apply(Update update);
}
