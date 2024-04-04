package by.st.meetingwithfriendsbot.telegram.commands;

import by.st.meetingwithfriendsbot.utils.CommandConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;

@Component
public class CommandsHandler {
    private final Map<String,Command> commands;

    public CommandsHandler(@Autowired StartCommand startCommand,
                        @Autowired CreateMeetingCommand createMeetingCommand,
                           @Autowired ViewMeetingCategoriesCommand viewMeetingCategoriesCommand) {
        this.commands = Map.of(
                CommandConstants.START, startCommand,
                CommandConstants.CREATE_MEETING, createMeetingCommand,
                CommandConstants.MEETING_CATEGORIES, viewMeetingCategoriesCommand,
                CommandConstants.RETURN_MAIN_MENU,startCommand
        );
    }

    public List<PartialBotApiMethod<?>> handleCommands(Update update){
        String command = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        var commandHandler = commands.get(command);
        if(commandHandler != null){
            return commandHandler.apply(update);
        } else {
            return  List.of(new SendMessage(String.valueOf(chatId), CommandConstants.INCORRECT_COMMAND));
        }
    }
}
