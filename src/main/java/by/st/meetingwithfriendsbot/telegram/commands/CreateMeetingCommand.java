package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.model.Meeting;
import by.st.meetingwithfriendsbot.model.UserEntity;
import by.st.meetingwithfriendsbot.service.MeetingService;
import by.st.meetingwithfriendsbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateMeetingCommand implements Command {
    private final MeetingService meetingService;
    private final UserService userService;
    @Override
    public List<PartialBotApiMethod<?>> apply(Update update) {
        UserEntity userEntity = userService.findUserByTelegram(update.getMessage().getFrom().getId());
        Meeting  meeting = new Meeting();
        meeting.setCreator(userEntity);

        SendMessage sendMessage = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Логика создания встречи");
        return List.of(sendMessage);
    }



    @Override
    public SendMessage applySendMessage(Update update) {
        return null;
    }
}