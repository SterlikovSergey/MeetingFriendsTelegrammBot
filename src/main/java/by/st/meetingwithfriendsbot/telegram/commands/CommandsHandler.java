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
                           @Autowired ViewMeetingCategoriesCommand viewMeetingCategoriesCommand,
                           @Autowired ViewVisaCategoriesCommand viewVisaCategoriesCommand,
                           @Autowired ViewFaqCategoriesCommand viewFaqCategoriesCommand),
                           @Autowired ViewMyLocation viewMylocation) {
        this.commands = Map.of(
                CommandConstants.START, startCommand,
                CommandConstants.CREATE_MEETING, createMeetingCommand,
                CommandConstants.MEETING_CATEGORIES, viewMeetingCategoriesCommand,
                CommandConstants.GET_VISA, viewVisaCategoriesCommand,
                CommandConstants.RETURN_MAIN_MENU, startCommand,
                CommandConstants.FAQ, viewFaqCategoriesCommand
                CommandConstants.MY_LOCATION,viewMylocation
        );
    }

    public List<PartialBotApiMethod<?>> handleCommands(Update update){
        String command = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if (update.getMessage().hasLocation()) {
            var commandHandler = commands.get(CommandConstants.MY_LOCATION);
            return commandHandler.apply(update);
        } else {
            var commandHandler = commands.get(command);
            if(commandHandler != null){
                return commandHandler.apply(update);
            } else {
                return  List.of(new SendMessage(String.valueOf(chatId), CommandConstants.INCORRECT_COMMAND));
            }
        }
    }
}
